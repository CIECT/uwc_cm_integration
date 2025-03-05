package coza.opencollab.sakai.cm.uwc;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCanonicalCourse;
import coza.opencollab.sakai.cm.SISClient;
import coza.opencollab.sakai.cm.SISCourseChange;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISCourseSet;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISEnrollmentSet;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;
import coza.opencollab.sakai.cm.Utils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Gillman
 *
 */
@Slf4j
public class OAuthSASIIntegrationClient implements SISClient {
	
	public static final String CONFIG_YEAR = "uwc.coursemanagement.year";
	public static final String CONFIG_MODULE_LIMIT = "uwc.coursemanagement.module.limit";
	public static final String SASI_WEBSERVICE_URL = "uwc.cm.sasi.webservice.url";
	private ServerConfigurationService serverConfigurationService;
	
//	private String token = null;
	private String subscriptionKey = null;
	private URIBuilder uriBuilder = null;

	// all the current values
	private Map<String, SISAcademicSession> academicSessions = new HashMap<String, SISAcademicSession>();
	private Map<String, SISAcademicSession> academicSessions2 = new HashMap<String, SISAcademicSession>();
	private Map<String, SASIModule> modules = new HashMap<String, SASIModule>();
	private Map<String, SASIModule> modules2 = new HashMap<String, SASIModule>();
	private Map<SASIModule, List<String>> enrolledStudents = new HashMap<SASIModule, List<String>>();
	
	private boolean isCourseSetsDone = false;
	private boolean isCanonicalDone = false;
	private boolean limitModules = false;
	private int moduleLimit = 0;

	public void init() {	
		
//		moduleLimit = serverConfigurationService.getInt(CONFIG_MODULE_LIMIT, 0);
//		if(moduleLimit > 0){
//			limitModules = true;
//		}
		
		String webservicePath = serverConfigurationService.getString("uwc.cm.sasi.webservice.url");

		subscriptionKey = serverConfigurationService.getString("uwc.cm.sasi.webservice.Ocp-Apim-Subscription-Key");
		
		try {
			uriBuilder = new URIBuilder(webservicePath);
		} catch (URISyntaxException e) {
			log.error("Could not create URIBuilder with path: " + webservicePath);
		}

	}

//	@Override
//	public String setOAuthToken() {
//		
//		token = null;
//		
//		String PUBLIC_CLIENT_ID = serverConfigurationService.getString("uwc.cm.sasi.webservice.PUBLIC_CLIENT_ID");
//		String AUTHORITY = serverConfigurationService.getString("uwc.cm.sasi.webservice.AUTHORITY");
//		String CLIENT_SECRET = serverConfigurationService.getString("uwc.cm.sasi.webservice.CLIENT_SECRET");
//		String TENANT_ID = serverConfigurationService.getString("uwc.cm.sasi.webservice.TENANT_ID");
//		String CLIENT_SCOPE = serverConfigurationService.getString("uwc.cm.sasi.webservice.CLIENT_SCOPE");
//
//		String[] scopes = new String[] { CLIENT_SCOPE }; // Scope required for accessing specific API
////		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(PUBLIC_CLIENT_ID)
////				.clientSecret(CLIENT_SECRET).tenantId(TENANT_ID).build();
////		TokenRequestContext requestContext = new TokenRequestContext().addScopes(scopes);
////		token = credential.getToken(requestContext).block().getToken();
//		log.info("ClientSecretCredential.getToken");
//		
//		TokenCredential clientSecretCredential = new ClientSecretCredentialBuilder().tenantId(TENANT_ID)
//			     .clientId(PUBLIC_CLIENT_ID)
//			     .clientSecret(CLIENT_SECRET)
//			     .build();
//		TokenRequestContext requestContext = new TokenRequestContext().addScopes(scopes);
//		Mono<AccessToken> accessToken = clientSecretCredential.getToken(requestContext);
//
//		//Comment this out later
//		log.info("Token: " + token);
//		return token;
//	}
	
	@Override
	public Collection<SISAcademicSession> getAcademicSessions() {
		ensureAcademicSessions();
		return academicSessions.values();
	}
	
	private void ensureAcademicSessions(){
		if (academicSessions.isEmpty()) {
			getAllAcademicSessions(getAcademicYear());
		}
	}
	
	private SISAcademicSession getAcademicSession(String key){
		ensureAcademicSessions();
		SISAcademicSession as = academicSessions.get(key);
		if(as == null){
			as = academicSessions2.get(key);
		}
		return as;
	}
	
	/**
	 * Get the academic year from sakai.properties, if it is not there the 
	 * default is current year.
	 * 
	 * @see coza.opencollab.sakai.cm.IntegrationClient#getAcademicYear()
	 */
	private String getAcademicYear() {
		int year = serverConfigurationService.getInt(CONFIG_YEAR, 0);
		return year != 0 ? String.valueOf(year) : String.valueOf(Calendar
				.getInstance().get(Calendar.YEAR));
	}
	
	private void ensureModules(){
		if (modules.isEmpty()) {
			getAllModules(getAcademicYear());
		}
	}
	
	private SASIModule getModule(String key){
		ensureModules();
		SASIModule m = modules.get(key);
		if(m == null){
			m = modules2.get(key);
		}
		return m;
	}

	@Override
	public Collection<SISCourseSet> getCourseSets(SISAcademicSession academicSession) {
		ensureModules();
		Set<String> ids = new HashSet<String>();
		for(SASIModule module: modules.values()){
		    if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
                //only do modules for this academic session
                continue;
            }
			ids.add(module.getModuleCode().substring(0, 3));
		}
		Set<SISCourseSet> courseSets = new HashSet<SISCourseSet>();
		for(String id: ids){
			SISCourseSet cs = new SISCourseSet(id);
			//XXX Can we set this with better data?
			cs.setId(id);
			cs.setTitle(id);
			cs.setDescription(id);
			cs.setCategory("Module");
			courseSets.add(cs);
		}
		return courseSets;
	}
	
	public Collection<SISMembership> getCourseSetMemberships(SISCourseSet courseSet){
		return null;
	}
	
	@Override
	public Collection<SISCanonicalCourse> getCanonicalCourses(
			SISAcademicSession academicSession) {
		ensureModules();
		Set<SISCanonicalCourse> canonicalCourses = new HashSet<SISCanonicalCourse>();
		for(SASIModule module: modules.values()){
		    if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
                //only do modules for this academic session
                continue;
            }
			String id = module.getCanonicalCourseReference();
			SISCanonicalCourse canonicalCourse = new SISCanonicalCourse(id);
			//XXX Can we set this with better values
			canonicalCourse.setId(id);
			canonicalCourse.setTitle(id);
			canonicalCourse.setDescription(id);
			canonicalCourses.add(canonicalCourse);
		}
		return canonicalCourses;
	}

	@Override
	public Collection<SISCourseOffering> getCourseOfferings(
			SISAcademicSession academicSession) {
		ensureModules();
		List<SISCourseOffering> courseOfferings = new ArrayList<SISCourseOffering>();
		for(SASIModule module: modules.values()){
			if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
				//only do modules for this academic session
				continue;
			}
			SISCourseOffering courseOffering = new SISCourseOffering(module.getModuleCode());
			//XXX Can we set this with better values
			courseOffering.setId(module.getCourseOfferingReference());
			courseOffering.setTitle(module.getCanonicalCourseReference());
			courseOffering.setDescription(module.getDescription());
			courseOffering.setStatus("Active");
			courseOffering.setAcademicSessionId(academicSession.getId());
			courseOffering.setCanonicalCourseId(module.getCanonicalCourseReference());
			courseOffering.setStartDate(academicSession.getStartDate());
			courseOffering.setEndDate(academicSession.getEndDate());
			courseOffering.addCourseSetIds(module.getModuleCode().substring(0, 3));
			courseOfferings.add(courseOffering);
		}
		return courseOfferings;
	}
	
	@Override
	public Collection<SISMembership> getCourseOfferingMemberships(SISCourseOffering courseOffering){
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		SASIModule module = getModule(courseOffering.getId());
		for (String studentId : getEnrolledStudents(module)) {
			SISMembership membership = new SISMembership(studentId);
			membership.setUserId(studentId);
			membership.setRole("S");
			membership.setStatus("Active");
			memberships.add(membership);
		}
		return memberships;
	}

	@Override
	public Collection<SISEnrollmentSet> getEnrollmentSets(SISCourseOffering courseOffering){
		SASIModule module = getModule(courseOffering.getSISCode());
		List<SISEnrollmentSet> enrollmentSets = new ArrayList<SISEnrollmentSet>();
		SISEnrollmentSet enrollmentSet = new SISEnrollmentSet(module.getEnrollmentSetReference());
		enrollmentSet.setId(module.getEnrollmentSetReference());
		enrollmentSet.setTitle(module.getEnrollmentSetReference());
		enrollmentSet.setDescription(module.getEnrollmentSetReference());
		enrollmentSet.setCategory("Students");
		enrollmentSet.setDefaultEnrollmentCredits("0");
		enrollmentSet.setCourseOfferingEid(courseOffering.getId());
		enrollmentSet.setOfficialInstructors(null);
		enrollmentSets.add(enrollmentSet);
		return enrollmentSets;
	}

	@Override
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet){
		List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();
		SASIModule module = getModule(enrollmentSet.getCourseOfferingEid());
		
		for (String studentId : getEnrolledStudents(module)) {
			SISEnrollment enrollment = new SISEnrollment(studentId);
			enrollment.setStudentId(studentId);
			enrollment.setEnrollmentSetId(enrollmentSet.getId());
			enrollment.setStatus("enrolled");
			enrollment.setCredits("0");
			enrollment.setGradingScheme("standard");
			enrollments.add(enrollment);
		}
		return enrollments;
	}

	@Override
	public Collection<SISSection> getSections(SISCourseOffering courseOffering){
		SASIModule module = getModule(courseOffering.getId());
		SISSection section = new SISSection(module.getCourseOfferingReference());
		section.setId(module.getCourseOfferingReference());
		section.setTitle(module.getCourseOfferingReference());
		section.setDescription(module.getCourseOfferingReference() + " Lecture");
		section.setCategoryCode("LCT");
		section.setCategoryDescription("Lecture");
		section.setParentSectionId(null);
		section.setCourseOfferingId(courseOffering.getId());
		section.setEnrollmentSetId(module.getEnrollmentSetReference());
		List<SISSection> ss = new ArrayList<SISSection>();
		ss.add(section);
		return ss;
	}
	
	@Override
	public Collection<SISMembership> getSectionMemberships(SISSection section){
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		SASIModule module = getModule(section.getCourseOfferingId());
		for (String studentId : getEnrolledStudents(module)) {
			SISMembership membership = new SISMembership(studentId);
			membership.setUserId(studentId);
			membership.setRole("S");
			membership.setStatus("Active");
			memberships.add(membership);
		}
		return memberships;
	}

	private Map<String, SISAcademicSession> getAllAcademicSessions(String year) {
//		Download_CalendarGroup_Input academicInput = new Download_CalendarGroup_Input();
//		academicInput.setYear(BigInteger.valueOf(Long.valueOf(year)));
//		Download_CalendarGroup_Output calendarOutput = null;
//		SISAcademicSession academicSession = null;
//		try {
//			calendarOutput = proxy.getSakaiSoap().download_CalendarGroup(
//					academicInput);
//			String[][] academicCalendarArray = calendarOutput
//					.getCalendar_Group_List();
//			for (int i = 0; i < academicCalendarArray.length; i++) {
//				if(academicCalendarArray[i].length < 5){
//					break;
//				}
//				academicSession = new SISAcademicSession(
//						academicCalendarArray[i][1],
//						academicCalendarArray[i][1] + " " + academicCalendarArray[i][0],
//						academicCalendarArray[i][2] + " " + academicCalendarArray[i][0],
//						academicCalendarArray[i][0],
//						Utils.getDateFromString(academicCalendarArray[i][3]),
//						Utils.getDateFromString(academicCalendarArray[i][4]),
//						"Academic Session for " + academicCalendarArray[i][0] + " "
//						+ academicCalendarArray[i][2]);
//				
//				academicSessions.put(academicSession.getSISCode(), academicSession);
//				academicSessions2.put(academicSession.getId(), academicSession);
//			}
//			return academicSessions;
//		} catch (RemoteException e) {
//			throw new SISException(e);
//		}
		

		SISAcademicSession academicSession = null;
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_CalendarGroup xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_CalendarGroupRequest>\n        <Year>"
					+ year
					+ "</Year>\n        <Calendar_Group>1</Calendar_Group>\n      </Download_CalendarGroupRequest>\n    </Download_CalendarGroup>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.info("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
//				String body = handler.handleResponse(response);				

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));

				NodeList nList = document.getElementsByTagName("row");
				log.info("============================");

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						for (int j = 0; j < childNodes.getLength(); j++) {
							log.info(
									"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}
						
						academicSession = new SISAcademicSession(
								eElement.getElementsByTagName("column").item(1).getTextContent(),
								eElement.getElementsByTagName("column").item(1).getTextContent() + " " + eElement.getElementsByTagName("column").item(0).getTextContent(),
								eElement.getElementsByTagName("column").item(2).getTextContent() + " " + eElement.getElementsByTagName("column").item(0).getTextContent(),
								eElement.getElementsByTagName("column").item(0).getTextContent(),
								Utils.getDateFromString(eElement.getElementsByTagName("column").item(3).getTextContent()),
								Utils.getDateFromString(eElement.getElementsByTagName("column").item(4).getTextContent()),
								"Academic Session for " + eElement.getElementsByTagName("column").item(0).getTextContent() + " "
								+ eElement.getElementsByTagName("column").item(2).getTextContent());
						
						academicSessions.put(academicSession.getSISCode(), academicSession);
						academicSessions2.put(academicSession.getId(), academicSession);
								
						log.info("============================");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}
		
		return academicSessions;
	}

	private Map<String, SASIModule> getAllModules(String year) {
//		Download_Modules_Input moduleInput = new Download_Modules_Input();
//		Download_Modules_Output moduleOutput = null;
//		moduleInput.setYear(BigInteger.valueOf(Long.valueOf(year)));
//		List<String> deptCodeList = getDepartmentCodeList();
//		SASIModule module = null;
//		try {
//			allout: for (String deptCode : deptCodeList) {
//				moduleInput.setDepartment_code(deptCode);
//				moduleOutput = proxy.getSakaiSoap().download_Modules(
//						moduleInput);
//				String[][] moduleArray = moduleOutput.getModule_List();
//				for (int i = 0; i < moduleArray.length; i++) {
//					if(limitModules && modules.size() > moduleLimit) break allout;
//					if(moduleArray[i].length < 6){
//						break;
//					}
//					module = new SASIModule();
//					module.setModuleCode(moduleArray[i][3]);
//					module.setDescription(moduleArray[i][4]);
//					module.setAcademicSessionCode(moduleArray[i][5]);
//					if (module.getModuleCode() != null) {
//						module.setAcademicSession(getAcademicSession(module.getAcademicSessionCode()));
//						modules.put(module.getModuleCode(), module);
//						modules2.put(module.getCourseOfferingReference(), module);
//					}
//				}
//			}
//			return modules;
//		} catch (RemoteException e) {
//			throw new SISException(e);
//		}
		
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
			
			List<String> deptCodeList = getDepartmentCodeList();
			SASIModule module = null;
//			allout: for (String deptCode : deptCodeList) {
			for (String deptCode : deptCodeList) {
				System.out.println("*****  Department: " + deptCode);

				String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Modules xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_ModulesRequest>\n        <Year>"
						+ year + "</Year>\n        <Department_code>" + deptCode
						+ "</Department_code>\n        <Module_code></Module_code>\n        <Module></Module>\n      </Download_ModulesRequest>\n    </Download_Modules>\n  </Body>\n</Envelope>";
				StringEntity entity = new StringEntity(xmlContent, "UTF-8");
				httpPost.setEntity(entity);

				log.info("http post: " + httpPost.toString());

				CloseableHttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {		
					
					HttpEntity httpEntity = response.getEntity();

		             // java.net.SocketException: Socket is closed exception thrown on next line..
		            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
		            log.info("Response String:\n" + responseString);
					
					// Parse the response using DocumentBuilder so you can get at elements easily
				    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				            .parse(new InputSource(new StringReader(responseString)));
				    			    
				    NodeList nList = document.getElementsByTagName("row");
				    log.info("============================");

				    for (int i = 0; i < nList.getLength(); i++) {
				      Node node = nList.item(i);
				    
				      if (node.getNodeType() == Node.ELEMENT_NODE) {
				        //Print each employee's detail
				        Element eElement = (Element) node;
				        
				        NodeList childNodes = eElement.getChildNodes();
				        
				        if(childNodes.getLength() == 0 || childNodes.getLength() == 1){
				        	continue;
				        }

				        for (int j = 0; j < childNodes.getLength(); j++) {
				        	log.info("column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
				        }
				        
//				        if(limitModules && modules.size() > moduleLimit) break allout;
//						if(moduleArray[i].length < 6){
//							break;
//						}
						module = new SASIModule();
						module.setModuleCode(eElement.getElementsByTagName("column").item(3).getTextContent());
						module.setDescription(eElement.getElementsByTagName("column").item(4).getTextContent());
						module.setAcademicSessionCode(eElement.getElementsByTagName("column").item(5).getTextContent());
						
						if (module.getModuleCode() != null) {
							module.setAcademicSession(getAcademicSession(module.getAcademicSessionCode()));
							modules.put(module.getModuleCode(), module);
							modules2.put(module.getCourseOfferingReference(), module);
						}
						log.info("============================");
				      }
				    }
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}		
		
		return modules;
	}

	private List<String> getDepartmentCodeList() {
		List<String> facultyCodeList = getFacultyCodeList(getFaculties());
		return getDepartmentCodeList(getDepartments(facultyCodeList));
	}

	private List<Faculty> getFaculties() {
		List<Faculty> facultyList = new ArrayList<Faculty>();
//		try {
//			Download_Faculty_Output facultyOutput = proxy.getSakaiSoap()
//					.download_Faculty(new Download_Faculty_Input());
//			String[][] facultyArray = facultyOutput.getFaculty_List();
//			Faculty faculty = null;
//			for (int i = 0; i < facultyArray.length; i++) {
//				if(facultyArray[i].length < 1){
//					break;
//				}
//				faculty = new Faculty();
//				faculty.setFacultyCode(facultyArray[i][0]);
//				facultyList.add(faculty);
//			}
//			return facultyList;
//		} catch (RemoteException e) {
//			throw new SISException(e);
//		}
		
		Faculty faculty = null;
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Faculty xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_FacultyRequest>\n        <Faculty></Faculty>\n      </Download_FacultyRequest>\n    </Download_Faculty>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.info("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {	

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));

				NodeList nList = document.getElementsByTagName("row");
				log.info("============================");

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						for (int j = 0; j < childNodes.getLength(); j++) {
							log.info("column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}						

						faculty = new Faculty();
						faculty.setFacultyCode(eElement.getElementsByTagName("column").item(0).getTextContent());
						facultyList.add(faculty);
						log.info("============================");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return facultyList;
	}

	private List<String> getFacultyCodeList(List<Faculty> facultyList) {
		List<String> facultyCodeList = new ArrayList<String>();
		for (Faculty integrationFaculty : facultyList) {
			if (integrationFaculty.getFacultyCode() != null
					&& integrationFaculty.getFacultyCode() != "") {
				facultyCodeList.add(integrationFaculty.getFacultyCode());
			}
		}
		return facultyCodeList;
	}

	private List<Department> getDepartments(List<String> facultyCodeList) {
		List<Department> departmentList = new ArrayList<Department>();
//		Download_Department_Input departmentInput = new Download_Department_Input();
//		Department department = null;
//		for (String facultyCode : facultyCodeList) {
//			departmentInput.setFaculty(facultyCode);
//			try {
//				Download_Department_Output departmentOutput = proxy
//						.getSakaiSoap().download_Department(departmentInput);
//				String[][] departmentArray = departmentOutput
//						.getDepartment_List();
//				for (int i = 0; i < departmentArray.length; i++) {
//					if(departmentArray[i].length < 1){
//						break;
//					}
//					department = new Department();
//					department.setDepartmentCode(departmentArray[i][0]);
//					departmentList.add(department);
//				}
//			} catch (RemoteException e) {
//				throw new SISException(e);
//			}
//		}
		
		Department department = null;
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);

//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			for (String facultyCode : facultyCodeList) {
				log.info("*****  Factulty: " + facultyCode);

				String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Department xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_DepartmentRequest>\n        <Department></Department>\n        <Department_code></Department_code>\n        <Faculty>" + facultyCode + "</Faculty>\n      </Download_DepartmentRequest>\n    </Download_Department>\n  </Body>\n</Envelope>";
				StringEntity entity = new StringEntity(xmlContent, "UTF-8");
				httpPost.setEntity(entity);

				log.info("http post: " + httpPost.toString());

				CloseableHttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {	

					HttpEntity httpEntity = response.getEntity();

					// java.net.SocketException: Socket is closed exception thrown on next line..
					String responseString = EntityUtils.toString(httpEntity, "UTF-8");
					log.info("Response String:\n" + responseString);

					// Parse the response using DocumentBuilder so you can get at elements easily
					Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse(new InputSource(new StringReader(responseString)));

					NodeList nList = document.getElementsByTagName("row");
					log.info("============================");

					for (int i = 0; i < nList.getLength(); i++) {
						Node node = nList.item(i);

						if (node.getNodeType() == Node.ELEMENT_NODE) {
							// Print each employee's detail
							Element eElement = (Element) node;

							NodeList childNodes = eElement.getChildNodes();

							for (int j = 0; j < childNodes.getLength(); j++) {
								log.info(
										"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
							}
							
							department = new Department();
							department.setDepartmentCode(eElement.getElementsByTagName("column").item(0).getTextContent());
							departmentList.add(department);
							log.info("============================");
						}
					}
				}
			}

		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return departmentList;
	}

	private List<String> getDepartmentCodeList(List<Department> departmentList) {
		List<String> deptCodeList = new ArrayList<String>();
		for (Department integrationDepartment : departmentList) {
			if (integrationDepartment.getDepartmentCode() != null
					&& integrationDepartment.getDepartmentCode() != "") {
				deptCodeList.add(integrationDepartment.getDepartmentCode());
			}
		}
		return deptCodeList;
	}

	private List<String> getEnrolledStudents(SASIModule module) {
		List<String> studentClassList = enrolledStudents.get(module);
		if (studentClassList != null) {
			return studentClassList;
		}
		studentClassList = new ArrayList<String>();
//		Download_Students_Input classListInput = new Download_Students_Input();
//		Download_Students_Output classListOutput = null;
//		classListInput.setYear(BigInteger.valueOf(Long.valueOf(module.getAcademicSession().getYear())));
//		classListInput.setModule_code(module.getModuleCode());
//		try {
//			classListOutput = proxy.getSakaiSoap().download_Students(
//					classListInput);
//			String[][] studentArray = classListOutput.getStudent_List();
//			for (int i = 0; i < studentArray.length; i++) {
//				if(studentArray[i].length < 1){
//					break;
//				}
//				studentClassList.add(studentArray[i][0]);
//			}
//			enrolledStudents.put(module, studentClassList);
//			return studentClassList;
//		} catch (RemoteException e) {
//			throw new SISException(e);
//		}

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			log.info("*****  Module: " + module.getModuleCode());

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Students xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_StudentsRequest>\n        <Module_code>"
					+ module.getModuleCode() + "</Module_code>\n        <Student></Student>\n         <Year>"
					+ module.getAcademicSession().getYear()
					+ "</Year>\n     </Download_StudentsRequest>\n    </Download_Students>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.info("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));

				NodeList nList = document.getElementsByTagName("row");
				log.info("============================");

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						if (eElement.getElementsByTagName("column").item(0).getTextContent() == "") {
							log.info("no students");
							continue;
						}

						for (int j = 0; j < childNodes.getLength(); j++) {
							log.info(
									"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
							
							studentClassList.add(eElement.getElementsByTagName("column").item(j).getTextContent());
						}
						enrolledStudents.put(module, studentClassList);
						log.info("============================");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}

		return studentClassList;
	}

	@Override
	public Collection<SISCourseChange> getCourseChanges() {
		List<SISCourseChange> courseChangeList = new ArrayList<SISCourseChange>();
//		Download_CourseChanges_Input courseChangeInput = new Download_CourseChanges_Input();
//		courseChangeInput.setYear(BigInteger.valueOf(Long.valueOf(getAcademicYear())));
//		Download_CourseChanges_Output courseChangeOutput = null;
//		SISCourseChange courseChange = null;
//		try {
//			courseChangeOutput = proxy.getSakaiSoap().download_CourseChanges(
//					courseChangeInput);
//			String[][] courseChangeArray = courseChangeOutput
//					.getCourse_Changes_List();
//			for (int i = 0; i < courseChangeArray.length; i++) {
//				if(courseChangeArray[i].length < 3){
//					break;
//				}
//
//				courseChange = new SISCourseChange();
//				courseChange.setStudentNum(courseChangeArray[i][0]);
//				courseChange.setModuleCode(courseChangeArray[i][1]);
//				courseChange.setMaintananceIndicator(courseChangeArray[i][2]);
//                if (courseChange.getModuleCode() != null) {
//                    courseChangeList.add(courseChange);
//                }
//			}
//			return courseChangeList;
//		} catch (RemoteException e) {
//			throw new SISException(e);
//		}

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_CourseChanges xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_CourseChangesRequest>\n        <Year>"
					+ getAcademicYear()
					+ "</Year>\n        <Course_Changes></Course_Changes>\n      </Download_CourseChangesRequest>\n    </Download_CourseChanges>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.info("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
//				String body = handler.handleResponse(response);				

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));

				NodeList nList = document.getElementsByTagName("row");
				log.info("============================");
				SISCourseChange courseChange = null;

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						if (eElement.getElementsByTagName("column").item(0).getTextContent() == "") {
							log.info("No course changes");
							continue;
						}

						for (int j = 0; j < childNodes.getLength(); j++) {
							log.info("column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}

						courseChange = new SISCourseChange();
						courseChange.setStudentNum(eElement.getElementsByTagName("column").item(0).getTextContent());
						courseChange.setModuleCode(eElement.getElementsByTagName("column").item(1).getTextContent());
						courseChange.setMaintananceIndicator(
								eElement.getElementsByTagName("column").item(2).getTextContent());
						if (courseChange.getModuleCode() != null) {
							courseChangeList.add(courseChange);
						}

						log.info("============================");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Could not encode for GET", e);
		} catch (IOException e) {
			log.error("IO error during GET and read", e);
		} catch (URISyntaxException e) {
			log.error("URI Syntax Error", e);
		} catch (SAXException e) {
			log.error("SAXException Error", e);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException Error", e);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return courseChangeList;
	}
	
	public void setServerConfigurationService(
			ServerConfigurationService serverConfigurationService) {
		this.serverConfigurationService = serverConfigurationService;
	}

	@Override
	public SISCourseOffering getCourseOffering(SISAcademicSession academicSession, SISCanonicalCourse canonicalCourse,
			String courseSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SISMembership> getSISMemberships(Collection<String> studentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getStudentList(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getLecturerList(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SISEnrollmentSet getEnrollmentSet(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet, Collection<String> studentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SISSection getSection(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void destroy() {

		academicSessions.clear();
		academicSessions2.clear();
		modules.clear();
		modules2.clear();
		enrolledStudents.clear();	
	}
}

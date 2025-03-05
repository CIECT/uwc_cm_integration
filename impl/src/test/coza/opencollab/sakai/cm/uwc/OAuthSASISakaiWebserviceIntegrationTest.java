package coza.opencollab.sakai.cm.uwc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Gillman
 *
 */
@Slf4j
public class OAuthSASISakaiWebserviceIntegrationTest {

	public static void main(String[] args) {

//		String token = getToken();

		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
//        SakaiSoapProxy proxy = new SakaiSoapProxy(
//                "http://196.11.235.60/applctns_sakai_WS/sakai.asmx");

		try {

			URIBuilder uriBuilder = new URIBuilder("https://az-jhb-uwc-apim-int-test-01.azure-api.net/sakai_api/v1/");
			
			// commented out before commit to Github
			String subscriptionKey = "";

			// Academic Sessions
	        getSASIAcademicSessions(uriBuilder, currentYear, subscriptionKey);
//			// Faculties
			List<String> facultyCodeList = getSASIFaculties(uriBuilder, subscriptionKey);
//	        //Faculties
			List<String> deptCodeList = getSASIDepartments(uriBuilder, subscriptionKey, facultyCodeList);
//	        //Modules
	        List<String> moduleCodeList = getSASIModules(uriBuilder, subscriptionKey, deptCodeList, currentYear);
//	        //Enrolled Students
	        getSASIEnrolledStudents(uriBuilder, subscriptionKey, moduleCodeList, currentYear);
	        
//	        //Course Change
//	        getSASICourseChange(uriBuilder, subscriptionKey, currentYear);

//			getModules(uriBuilder, subscriptionKey, deptCodeList, currentYear);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private static String getToken() {
//		
//		//values removed, must not be committed to Github, only to be used for local testing and debugging
//
//		String PUBLIC_CLIENT_ID = "";
//		String AUTHORITY = "";
//		String CLIENT_SECRET = "";
//		String TENANT_ID = "";
//		String CLIENT_SCOPE = "";
//
//		String token = "";
//		
//		String[] scopes = new String[] { CLIENT_SCOPE };
////		ClientSecretCredential credential = new ClientSecretCredentialBuilder()
////				.clientId(PUBLIC_CLIENT_ID)
////				.clientSecret(CLIENT_SECRET)
////				.tenantId(TENANT_ID)
////				.build();
////		TokenRequestContext requestContext = new TokenRequestContext().addScopes(scopes);
////		token = credential.getToken(requestContext).block().getToken();
////		System.out.println(token);
//		
//
//		
////		TokenCredential clientSecretCredential = new ClientSecretCredentialBuilder().tenantId(TENANT_ID)
////			     .clientId(PUBLIC_CLIENT_ID)
////			     .clientSecret(CLIENT_SECRET)
////			     .build();
////		TokenRequestContext requestContext = new TokenRequestContext().addScopes(scopes);
////		token = clientSecretCredential.getToken(requestContext).block().getToken();
//
//		return token;
//
//	}

//	private static void getModules(URIBuilder uriBuilder, String token, List<String> deptCodeList, int currentYear) {
//		System.out.println("*****   UWC Modules   ********");
//		System.out.println("");
//		Download_Modules_Input moduleInput = new Download_Modules_Input();
//		moduleInput.setYear(BigInteger.valueOf(currentYear));
//		Download_Modules_Output moduleOutput = null;
//		StringBuilder stringBuilder = new StringBuilder("");
//		List<String> moduleCodeList = new ArrayList<String>();
//		try {
//			for (String deptCode : deptCodeList) {
//				moduleInput.setDepartment_code(deptCode);
//				moduleOutput = proxy.getSakaiSoap().download_Modules(moduleInput);
//				String[][] moduleArray = moduleOutput.getModule_List();
//				for (int i = 0; i < moduleArray.length; i++) {
//					// for (int i = 0; i < 5; i++) {
//					for (int j = 0; j < moduleArray[i].length; j++) {
//						// for (int j = 0; j < 5; j++) {
//						String module = moduleArray[i][j];
//						if (j == 0) {
//							moduleCodeList.add(module);
//						}
//						stringBuilder.append(module);
//						if (j != moduleArray[i].length) {
//							stringBuilder.append(", ");
//						}
//					}
//					System.out.println(stringBuilder);
//					stringBuilder = new StringBuilder("");
//				}
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			// TODO Auto-generated catch block
//		}
//		sortModuleList(moduleCodeList);
//	}

	private static void sortModuleList(List<String> moduleCodeList) {
		Collections.sort(moduleCodeList, new Comparator<String>() {

			public int compare(String moduleCode1, String moduleCode2) {
				return moduleCode1.compareTo(moduleCode2);
			}
		});
	}

	private static void getSASIEnrolledStudents(URIBuilder uriBuilder, String subscriptionKey, List<String> moduleCodeList,
			int currentYear) {
		System.out.println("*****   UWC Enrolled Students   ********");
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			for (String moduleCode : moduleCodeList) {
				System.out.println("*****  Module: " + moduleCode);

				String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Students xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_StudentsRequest>\n        <Module_code>"
						+ moduleCode + "</Module_code>\n        <Student></Student>\n         <Year>" + currentYear
						+ "</Year>\n     </Download_StudentsRequest>\n    </Download_Students>\n  </Body>\n</Envelope>";
				StringEntity entity = new StringEntity(xmlContent, "UTF-8");
				httpPost.setEntity(entity);

				log.debug("http post: " + httpPost.toString());

				CloseableHttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				
				if (statusCode == 200) {		
					
					HttpEntity httpEntity = response.getEntity();

		             // java.net.SocketException: Socket is closed exception thrown on next line..
		            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
		            System.out.println("Response String:\n" + responseString);
					
					// Parse the response using DocumentBuilder so you can get at elements easily
				    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				            .parse(new InputSource(new StringReader(responseString)));
				    Element root = document.getDocumentElement();
				    			    
				    NodeList nList = document.getElementsByTagName("row");
				    System.out.println("============================");
				    ArrayList<String> nameValues = null;

				    for (int i = 0; i < nList.getLength(); i++) {
				      Node node = nList.item(i);
				    
				      if (node.getNodeType() == Node.ELEMENT_NODE) {
				        //Print each employee's detail
				        Element eElement = (Element) node;
				        
				        NodeList childNodes = eElement.getChildNodes();
				        
				        if(eElement.getElementsByTagName("column").item(0).getTextContent() == ""){
						    System.out.println("no students");
				        	continue;
				        }

				        for (int j = 0; j < childNodes.getLength(); j++) {
					        System.out.println("column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
				        }
//				        moduleCodeList.add(eElement.getElementsByTagName("column").item(3).getTextContent());
					    System.out.println("============================");
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

		System.out.println("");
		System.out.println("");
	}

	private static List<String> getSASIModules(URIBuilder uriBuilder, String subscriptionKey, List<String> deptCodeList,
			int currentYear) {
		List<String> moduleCodeList = new ArrayList<String>();
		System.out.println("*****   UWC Modules   ********");
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			for (String deptCode : deptCodeList) {
				System.out.println("*****  Department: " + deptCode);

				String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Modules xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_ModulesRequest>\n        <Year>"
						+ currentYear + "</Year>\n        <Department_code>" + deptCode
						+ "</Department_code>\n        <Module_code></Module_code>\n        <Module></Module>\n      </Download_ModulesRequest>\n    </Download_Modules>\n  </Body>\n</Envelope>";
				StringEntity entity = new StringEntity(xmlContent, "UTF-8");
				httpPost.setEntity(entity);

				log.debug("http post: " + httpPost.toString());

				CloseableHttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {		
					
					HttpEntity httpEntity = response.getEntity();

		             // java.net.SocketException: Socket is closed exception thrown on next line..
		            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
		            System.out.println("Response String:\n" + responseString);
					
					// Parse the response using DocumentBuilder so you can get at elements easily
				    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				            .parse(new InputSource(new StringReader(responseString)));
				    Element root = document.getDocumentElement();
				    			    
				    NodeList nList = document.getElementsByTagName("row");
				    System.out.println("============================");
				    ArrayList<String> nameValues = null;

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
					        System.out.println("column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
				        }
				        moduleCodeList.add(eElement.getElementsByTagName("column").item(3).getTextContent());
					    System.out.println("============================");
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

		System.out.println("");
		System.out.println("");
		return moduleCodeList;
	}

	private static List<String> getSASIDepartments(URIBuilder uriBuilder, String subscriptionKey, List<String> facultyCodeList) {
		List<String> deptCodeList = new ArrayList<String>();
		System.out.println("*****   UWC Departments   ********");
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);

//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			for (String facultyCode : facultyCodeList) {
				System.out.println("*****  Factulty: " + facultyCode);

				String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Department xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_DepartmentRequest>\n        <Department></Department>\n        <Department_code></Department_code>\n        <Faculty>" + facultyCode + "</Faculty>\n      </Download_DepartmentRequest>\n    </Download_Department>\n  </Body>\n</Envelope>";
				StringEntity entity = new StringEntity(xmlContent, "UTF-8");
				httpPost.setEntity(entity);

				log.debug("http post: " + httpPost.toString());

				CloseableHttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {	

					HttpEntity httpEntity = response.getEntity();

					// java.net.SocketException: Socket is closed exception thrown on next line..
					String responseString = EntityUtils.toString(httpEntity, "UTF-8");
					System.out.println("Response String:\n" + responseString);

					// Parse the response using DocumentBuilder so you can get at elements easily
					Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse(new InputSource(new StringReader(responseString)));
					Element root = document.getDocumentElement();

					NodeList nList = document.getElementsByTagName("row");
					System.out.println("============================");
					ArrayList<String> nameValues = null;

					for (int i = 0; i < nList.getLength(); i++) {
						Node node = nList.item(i);

						if (node.getNodeType() == Node.ELEMENT_NODE) {
							// Print each employee's detail
							Element eElement = (Element) node;

							NodeList childNodes = eElement.getChildNodes();

							for (int j = 0; j < childNodes.getLength(); j++) {
								System.out.println(
										"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
							}
							deptCodeList.add(eElement.getElementsByTagName("column").item(0).getTextContent());
							System.out.println("============================");
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

		System.out.println("");
		System.out.println("");
		return deptCodeList;
	}

	private static List<String> getSASIFaculties(URIBuilder uriBuilder, String subscriptionKey) {

		List<String> facultyCodeList = new ArrayList<String>();
		System.out.println("*****   UWC Faculties   ********");
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_Faculty xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_FacultyRequest>\n        <Faculty></Faculty>\n      </Download_FacultyRequest>\n    </Download_Faculty>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.debug("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {	

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));
				Element root = document.getDocumentElement();

				NodeList nList = document.getElementsByTagName("row");
				System.out.println("============================");
				ArrayList<String> nameValues = null;

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						for (int j = 0; j < childNodes.getLength(); j++) {
							System.out.println(
									"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}
						facultyCodeList.add(eElement.getElementsByTagName("column").item(0).getTextContent());
						System.out.println("============================");
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
		
		System.out.println("");
		System.out.println("");
		return facultyCodeList;
	}

	private static void getSASIAcademicSessions(URIBuilder builder, int currentYear, String subscriptionKey) {

		System.out.println("*****   UWC Academic Sessions   ********");
		System.out.println("*****   Year: " + currentYear);
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(builder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_CalendarGroup xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_CalendarGroupRequest>\n        <Year>"
					+ currentYear
					+ "</Year>\n        <Calendar_Group>1</Calendar_Group>\n      </Download_CalendarGroupRequest>\n    </Download_CalendarGroup>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.debug("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
//				String body = handler.handleResponse(response);				

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));
				Element root = document.getDocumentElement();

				NodeList nList = document.getElementsByTagName("row");
				System.out.println("============================");
				ArrayList<String> nameValues = null;

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						for (int j = 0; j < childNodes.getLength(); j++) {
							System.out.println(
									"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}
						System.out.println("============================");
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

		System.out.println("");
		System.out.println("");
	}

	private static void getSASICourseChange(URIBuilder uriBuilder, String subscriptionKey, int currentYear) {

		System.out.println("*****   UWC Course changes   ********");
		System.out.println("");

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(uriBuilder.build());

//			httpPost.setHeader("Authorization", serverConfigurationService.getString("namecoach.auth_token", ""));
//			httpPost.setHeader("Authorization", token);
			httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			httpPost.setHeader("Accept", "text/xml");
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
						
			String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <Body>\n    <Download_CourseChanges xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.uwc.ac.za/\">\n      <Download_CourseChangesRequest>\n        <Year>" + currentYear + "</Year>\n        <Course_Changes></Course_Changes>\n      </Download_CourseChangesRequest>\n    </Download_CourseChanges>\n  </Body>\n</Envelope>";
			StringEntity entity = new StringEntity(xmlContent, "UTF-8");
			httpPost.setEntity(entity);

			log.debug("http post: " + httpPost.toString());

			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
//				String body = handler.handleResponse(response);				

				HttpEntity httpEntity = response.getEntity();

				// java.net.SocketException: Socket is closed exception thrown on next line..
				String responseString = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("Response String:\n" + responseString);

				// Parse the response using DocumentBuilder so you can get at elements easily
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new InputSource(new StringReader(responseString)));
				Element root = document.getDocumentElement();

				NodeList nList = document.getElementsByTagName("row");
				System.out.println("============================");
				ArrayList<String> nameValues = null;

				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Print each employee's detail
						Element eElement = (Element) node;

						NodeList childNodes = eElement.getChildNodes();

						for (int j = 0; j < childNodes.getLength(); j++) {
							System.out.println(
									"column: " + eElement.getElementsByTagName("column").item(j).getTextContent());
						}
						System.out.println("============================");
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

		System.out.println("");
		System.out.println("");
	}

	private void writeToFile(String fileName, String content) {
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object
		try {
			// Create a new file output stream
			// connected to "DevFile.txt"
			// Connect print stream to the output stream
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
	}
}

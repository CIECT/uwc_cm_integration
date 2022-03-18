package coza.opencollab.sakai.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.login.api.LoginAdvisor;
import org.sakaiproject.login.api.LoginCredentials;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

public class AddUserAsAdminLoginAdvisor implements LoginAdvisor {
	private final Log log = LogFactory.getLog(getClass());
	private String type;
	private String roleId = "admin";
	private boolean active = true;
	private boolean provided = true;
	private UserDirectoryService userService;
	private SiteService siteService;
	
	public void init(){
		ComponentManager.loadComponent(LoginAdvisor.class, this);
	}

	public boolean checkCredentials(LoginCredentials credentials) {
		return true;
	}
	
	public String getLoginAdvice(LoginCredentials credentials) {
		return "";
	}
	
	public boolean isAdvisorEnabled() {
		return type != null;
	}
	
	public void setFailure(LoginCredentials credentials) {}
	
	public void setSuccess(LoginCredentials credentials) {
		User user;
		try {
			user = userService.getUserByEid(credentials.getIdentifier());
			log.info("Got user " + user.getDisplayName() + " using eid " + credentials.getIdentifier());
		} catch (UserNotDefinedException e) {
			try {
				user = userService.getUser(credentials.getIdentifier());
				log.info("Got user " + user.getDisplayName() + " using id " + credentials.getIdentifier());
			} catch (UserNotDefinedException e1) {
				log.error("Got nothing using " + credentials.getIdentifier());
				return;
			}
		}
		if(type.equals(user.getType())){
			try {
				Site site = siteService.getSite("!admin");
				if(site.getMember(user.getId()) == null){
					site.addMember(user.getId(), roleId, active, provided);
					siteService.save(site);
					log.info("Added user " + user.getDisplayName() + " as admin");
				}else{
					log.info("User " + user.getDisplayName() + " already admin");
				}
			} catch (IdUnusedException e) {
				log.error("Could not get the admin site", e);
			} catch (PermissionException e) {
				log.error("Could not save site", e);
			}
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}

	public UserDirectoryService getUserService() {
		return userService;
	}

	public void setUserService(UserDirectoryService userService) {
		this.userService = userService;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}

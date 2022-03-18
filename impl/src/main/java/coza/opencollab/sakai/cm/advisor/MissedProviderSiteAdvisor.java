package coza.opencollab.sakai.cm.advisor;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteAdvisor;
import org.sakaiproject.site.api.SiteService;

public class MissedProviderSiteAdvisor implements SiteAdvisor {
	private SiteService siteService;

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public void init() {
		siteService.addSiteAdvisor(this);
	}

	public void update(Site site) {
		if("course".equals(site.getType()) && site.getProviderGroupId() == null){
			String providerId = site.getProperties().getProperty("site.cm.requested");
			if(providerId != null && providerId.trim().length() != 0){
				site.setProviderGroupId(providerId);
			}
		}
	}
}

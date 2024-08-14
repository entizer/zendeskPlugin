package com.morpheusdata.task

import com.morpheusdata.core.Plugin
import com.morpheusdata.views.HandlebarsRenderer
import com.morpheusdata.views.ViewModel
import com.morpheusdata.model.Permission

/**
 * An Example Task plugin
 */
class ZenDeskTaskPlugin extends Plugin {

	@Override
	String getCode() {
		return 'morpheus-zendesk-task-plugin'
	}

	/**
	 * <ul>
	 * <li>Initializes the plugin name, description, and author.</li>
	 * <li>Registers the task provider</li>
	 * <li>Registers a Handlebars template renderer</li>
	 * <li>Registers an example Controller</li>
	 * <li>Demonstrates rendering a template</li>
	 * </ul>
	 */
	@Override
	void initialize() {
		ZenDeskTaskProviderCreateTicket createTicket = new ZenDeskTaskProviderCreateTicket(this, morpheus)
		ZenDeskTaskProviderCreateObject createObject = new ZenDeskTaskProviderCreateObject(this, morpheus)
		ZenDeskTaskProviderCreateRequest createRequest = new ZenDeskTaskProviderCreateRequest(this, morpheus)
		this.pluginProviders.put(createTicket.code, createTicket)
		this.pluginProviders.put(createObject.code, createObject)
		this.pluginProviders.put(createRequest.code, createRequest)
		this.setName("Zendesk Task Plugin")
		this.setDescription("Provides interaction with ZenDesk ITSM")
		this.setAuthor("Korey Gawronski")
		this.setRenderer(new HandlebarsRenderer(this.classLoader))
		this.controllers.add(new ZenDeskTaskController(this, morpheus))
		def model = new ViewModel<String>()
		model.object = "Eric"
		println this.getRenderer().renderTemplate('instanceTab', model).html
	}

	/**
	 * Called when a plugin is being removed from the plugin manager (aka Uninstalled)
	 */
	@Override
	void onDestroy() {
		morpheus.task.disableTask('zenDeskCreateTicket').blockingGet()
		morpheus.task.disableTask('zenDeskCreateObject').blockingGet()
		morpheus.task.disableTask('zenDeskCreateRequest').blockingGet()
	}

	@Override
	public List<Permission> getPermissions() {
		Permission permission = new Permission('ZenDesk Plugin', 'zenDeskPlugin', [Permission.AccessType.full])
		return [permission];
	}

}

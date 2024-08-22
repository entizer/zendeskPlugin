package com.morpheusdata.task

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.OptionSourceProvider
import com.morpheusdata.core.Plugin

class ZenDeskOptionSourceProvider implements OptionSourceProvider {
    MorpheusContext morpheusContext
    Plugin plugin

    ZenDeskOptionSourceProvider(Plugin plugin, MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
    }

    @Override
    MorpheusContext getMorpheus() {
        return this.morpheusContext
    }

    @Override
    Plugin getPlugin() {
        return this.plugin
    }

    @Override
    String getCode() {
        return 'zendesk-option-source-plugin'
    }

    @Override
    String getName() {
        return 'ZenDesk Option Source Plugin'
    }

    @Override
    List<String> getMethodNames() {
        return new ArrayList<String>(['zenDeskPriorityTypeList'])
    }

    def zenDeskPriorityTypeList(args){
        return [
                [name: 'Low', value: 'low'],
                [name: 'Normal', value: 'medium'],
                [name: 'High', value: 'high'],
                [name: 'Urgent', value: 'urgent']
        ]
    }
}

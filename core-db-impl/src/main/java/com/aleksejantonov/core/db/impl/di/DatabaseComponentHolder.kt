package com.aleksejantonov.core.db.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.db.impl.di.DatabaseComponent.Companion.DATABASE_COMPONENT_KEY
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ComponentAssociatedData

object DatabaseComponentHolder : ComponentsHolder<CoreDatabaseApi, DatabaseComponentDependencies, ComponentAssociatedData>() {

    override fun initComponent(dependencies: DatabaseComponentDependencies): Pair<CoreDatabaseApi, String> {
        // No need to store singleton DatabaseComponent
        return DatabaseComponent.init(dependencies) to DATABASE_COMPONENT_KEY
    }

    override fun restoreComponent(niceToKeepComponentKey: String, associatedData: ComponentAssociatedData?): Pair<CoreDatabaseApi, String> {
        throw IllegalAccessException("No need to restore singleton DatabaseComponent")
    }

}
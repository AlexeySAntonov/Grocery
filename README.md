![Build](https://github.com/AlexeySAntonov/Grocery/actions/workflows/build_sign_upload.yml/badge.svg)
# Grocery
A shopping list application.<br/>
The application makes everyday shopping easier by enabling the user to list down items to buy.<br/>
While shopping, the application also helps to track items that are already collected.

## Architecture
### Vertically
MVVM + Clean
### Horizontally
Multi-module: Core modules and Feature modules weakly connected by Dagger.
#### BuildSrc module
Java module with global const dependencies and versions.<br>Visible for all other modules.
#### APP module
Main module of the application responsible for "gluing" feature modules and providing single activity as navigation host.
#### CORE-UI-BASE module
Contains all the basic/reusable classes for work with UI, useful extensions, helpers and shared XML resources as well.<br>Visible for all feature modules.
#### CORE-UI-MODEL
Contains all the UI models both specific and shared.<br/>Visible for all feature modules through transitive dependency in the CORE-UI-BASE module.
#### CORE-MODEL
Contains all the business models both specific and shared.<br/>
In this case as an exception contains DTOs as well.<br/>
Visible for all feature modules through transitive dependency in the CORE-UI-MODEL module.<br/>
Visible for database CORE-DB-API and CORE-DB-IMPL modules through transitive dependency in the CORE-DB-ENTITY module.
#### CORE-DB-API
API which provides *Store(s) for interaction with both local and remote databases.<br/>
Visible for all feature modules.
#### CORE-DB-IMPL
Stores and databases implementation.<br/>
Visible only for the App module.
#### CORE-DB-ENTITY
Contains all the database models and mappers.<br/>
Visible only for CORE-DB-API and CORE-DB-IMPL
#### CORE-NAVIGATION
Provides basic functionality and transitions for navigation.<br/>
GlobalRouter is responsible for handling global navigation commands and sends them to registered local Navigator if needed.<br/>
Navigator is responsible for handling local navigation commands through Navigation.<br/>
GlobalRouter is visible for all feature modules, but navigator implementation is hidden.
#### CORE-DI
Provides common DI annotations and GlobalFeatureProvider.<br/>
In the same time, features' screens implementation inside GlobalFeatureProvider are hidden by platform androidx.fragment.app.Fragment.
#### MODULE-INJECTOR
Provides useful abstractions for storing and recreation DI components.
#### FEATURE-TROLLEYLIST-API
Entry point for interaction with TrolleyList flow.<br/>
Can be visible for all interested modules.
#### FEATURE-TROLLEYLIST-IMPL
Hidden implementation of the feature (Trolleys' list and Trolley create screens).<br/>
Visible only for APP module for "gluing".
#### FEATURE-TROLLEYDETAILS-API
Entry point for interaction with TrolleyDetails flow.<br/>
Can be visible for all interested modules.
#### FEATURE-TROLLEYDETAILS-IMPL
Hidden implementation of the feature (TrolleyDetails screen).<br/>
Visible only for APP module for "gluing".
## Screenshots
<p float="left">
    <img src="https://user-images.githubusercontent.com/22200341/119635696-9d28f180-be1c-11eb-899f-26b4b3c67d21.jpg" width=33% height=33%>
    <img src="https://user-images.githubusercontent.com/22200341/119635692-9c905b00-be1c-11eb-871b-fb62481d2bc3.jpg" width=33% height=33%>
    <img src="https://user-images.githubusercontent.com/22200341/119635687-9ac69780-be1c-11eb-8d24-5c2d2514567a.jpg" width=33% height=33%>
</p>

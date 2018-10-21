Quaker Tracker
===============

### Environment
1. Android Studio 3.2.1
1. API version info: `Compile SDK Version: 28`; `Min SDK Version: 19`; `Target SDK Version: 28`
1. Auto-generated the Java classes needed to capture response (GeoJSON) from USGS endpoint using [jsonschema2pojo](http://www.jsonschema2pojo.org/)

### API Endpoint
1. The USGS' earthquake data endpoint provides 30 day records of varying data sets such as significant earthquakes or earthquakes with magnitude 1.0 and above or all earthquakes and while the `UsgsRestClient` provides calls for all three data sets, I'm leveraging 
the response from magnitude 1.0 and above 

### App Notes (branch: mapbox_integration)
1. Built the app using the Model-View-Pattern (MVP) pattern where the View (Activity) communicates with the Presenter for handling user interactions
1. The Presenter communicates with the Model to fetch the data from the API endpoint(s) 
1. The Retrofit client `UsgsRestClient` handles the request/response with the API endpoint(s) and is part of the Model (of MVP pattern) 
1. This Retrofit client implements a callback for success and failure scenarios
1. The objects in the Model are just POJOs and do not participate in massaging the data for presentation in the UI; that responsibility is handled by the Adapter
1. The show/hide functionality switches between showing maps with markers versus a list view of earthquakes
1. Tested the app on Pixel 2 (running OS 9) and an emulator (Pixel 2 running OS 8) 

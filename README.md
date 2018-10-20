Quaker Tracker
===============

### Environment
1. Android Studio 3.2.1
1. API version info: `Compile SDK Version: 28`; `Min SDK Version: 19`; `Target SDK Version: 28`
1. Auto-generated the Java classes needed to capture response (GeoJSON) from USGS endpoint using [jsonschema2pojo](http://www.jsonschema2pojo.org/)

### API Endpoint
1. For the sake of simplicity and since USGS endpoint does not provide a mechanism to filter the results to a shorter list, I'm fetching 30 day records of [Significant Earthquakes](https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson)
1. That said, I could have fetched either the [Magnitude 1.0 and above](https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_month.geojson) or [All Earthquakes](https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson) while displaying a progress spinner/bar and cached the records locally or in-memory for subsequent display and to prevent more network calls

### App Notes
1. Built the app using the Model-View-Pattern (MVP) pattern where the View (Activity) communicates with the Presenter only for handling user interactions as well as for communicating with the model to fetch the data from the APIs
1. The Retrofit client `UsgsRestClient` is part of the Model (of MVP pattern) since it calls the API endpoint(s)
1. This Retrofit client implements a callback for success and failure scenarios
1. The objects in the Model are just POJOs and do not participate in massaging the data for presentation in the UI, that responsibility is handled by the Adapter
1. To maintain the position for when the user returns from detail view, I leveraged the Activity lifecycle methods, `onPause()` and `onResume` to persist the row item user selected and hence to return the user to that same position, if the user chose not to hide that row item
1. At the moment, the show/hide functionality is a WIP and so,
    1. show all doesn't refresh with displaying the hidden item(s)
    1. if the user decides to display all (including hidden) row items, then the user can't go back to the hidden row items
1. Also, the user has to manually disable the display all switch in order to leverage the hide row item capability
1. Tested the app on Pixel 2 (running OS 9)  
   
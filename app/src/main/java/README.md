Earthquake Tracker
=====================

## Environment
1. Android Studio 3.1.1
2. compileSdkVersion 26; minSdkVersion 19; targetSdkVersion 26
3. auto-generated the Java classes needed to capture GeoJSON using [jsonschema2pojo](http://www.jsonschema2pojo.org/)
4. for this project, fetching [Significant Earthquakes](https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson) only for the past 30 days especially since the USGS endpoint doesn't provide a mechanism to filter the results to a shorter list
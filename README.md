# Whitelabel Android App

## Overview
Code Challenge sample

## App Requirements
Your app should be written entirely in Kotlin for Android. Try to demonstrate your knowledge of both basic and advanced language semantics.
We are not yet using Compose on our mobile projects, so please complete the exercise using XML layouts for Android.

Write a sample app that fetches and displays data from a RESTful Web API. The app should be comprised of two parts, a list and a detail.
Your app should support both portrait and landscape orientations on both phones and tablets.
On Phones, the list and detail should be separate screens, on Tablets, list and detail should appear on the same screen.

- For the list view, data should be displayed as a text only, vertically scrollable list of character names.
- The app should offer search functionality that filters the character list according to characters whose titles or descriptions contain the query
text
- Clicking on an item should load the detail view of that character, including the character’s image, title, and description. You choose the layout
of the detail.
- For the image in the detail view, use the URL in the "Icon" field of the API JSON response. For items with blank or missing image URLs, use
a placeholder image of your choice.
- Two variants of the app should be created, using a single shared codebase. Each variant should have a different name, package-name, and
url that it pulls data from. (We're interested in your methodology for creating multiple apps from a shared codebase)

### Variant One
- Name: Simpsons Character Viewer
- Data API: http://api.duckduckgo.com/?q=simpsons+characters&format=json
- Package name: com.sample.simpsonsviewer

### Variant Two
- Name: The Wire Character Viewer
- Data API: http://api.duckduckgo.com/?q=the+wire+characters&format=json
- Package name: com.sample.wireviewer

## Miscellaneous
Using Libraries To Complete The Task
Use open-source libraries as you see fit, but we must be able to build and run your project in the IDE. Before sending, consider building and
running your project from a clean environment.
Aside from the libraries you use, please disclose any other sources from which you have taken code or used as a bootstrap, other than the
templates provided by the IDE. This includes sample apps from other projects or your own pre-written bootstrap app code.

# AccentWatch

An Android project I thought was a good idea. I want to track how my english changes over time.

## Features

 * [x] Recording
 * [x] Playback
 * [ ] Reminders every 14 days
 * [ ] Cloud storage and backup
 * [ ] Users

## Flows

Here are a list of basic flows.

### Basic User Opens App

 1. User opens app
 2. User is asked to log in with Google account if we don't know who she is
 3. User presses record
   * If we wanted, this would be when we would take more data (location, time of day, etc). 
 4. User presses stop
 5. App begins to upload in background

### User gets notification

 1. User gets a notification (we won't notify if user has never logged in)
 2. User is given a suggested topic to talk about
 3. User presses record and talks
 4. User presses stop
 5. App begins to upload in background

### User views historical data 

 1. User opens app
 2. User is asked to log in with Google account if we don't know who she is
 3. User clicks list
 4. App makes sure recording list is up to date
 5. User clicks an entry
 6. A view showing pause and progress appears and plays back recording
 

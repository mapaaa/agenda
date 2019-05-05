# Agenda

Simple organizer in Java.

## Main objects
* Agenda
* AgendaEntry
* CalendarEntry which are Event, Reminder
* Note
* Task

## Actions
There are two major services:
* AccountManager - user related information such as First Name, Last Name, Date of birth etc
  * GetUser
  * ModifyUser

* AgendaManager - all tasks, reminders, notes etc operations
  * AddTask
  * AddEvent
  * AddReminder
  * AddNote
  * CompleteTask
  * GetAllTasks
  * GetAllAgendaEntries
  * GetAllReminders
  * GetAllNotes
  * GetAllEvents
  * GetTaskById

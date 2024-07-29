# Requirements Finalisation

## Meeting Details
- **Date:** 26/7/24
- **Time:** 2PM
- **Location:** CO242A
- **Attendees:** Alex, Rene, Joseph, Anthony, Nate

## Agenda
Finish project initial requirements which involves
- Making sure all requirements are S.M.A.R.T (invloves adding sufficient detail to each and making sure they are time bound)
- Adding some more requirements now that we have a more specific game idea. Maybe some of the ones listed here https://gitlab.ecs.vuw.ac.nz/manninalex/engr302-project/-/blob/main/project_management/meeting_notes/Meeting_Notes_3_24.7.24.md?ref_type=heads
- Read https://gitlab.ecs.vuw.ac.nz/manninalex/engr302-project/-/blob/main/project_management/Plan%20and%20Requirements%20analysis%20info.pdf?ref_type=heads and confirm that we think we have met everything that is required of our requirements

## Discussion Points
1. Discussed using Java as the back end server instead of Socket.io. This is because it would be easier to set up and the development team are more familiar with Java. The [Spring](https://spring.io/) framework could be used to implement a RESTful API that communicates between the client and the server. Graphic rendering would still be completed on the client side. Model, game stat and logic to be completed server side.

1. Brainstorm game aspects we like and come to a group consensus on the idea

##### Game Idea Aspects That we Like:

- Character burn out - characters blow up or become useless if they get burnt out
- Kanban 
- Tickets having priority
- Project manager with overarching control of board
- Random events such as workers getting sick or random tasks
- Blocks instead of tickets with different size and colour representing different ascents of a ticket such as difficulty and priority

#### Current game idea

Kanban Board with at least three roles - Project Manager, Developer, Tester, Maybe a review.

An undefined amount of tasks to be done within a given timeframe with the aim to do as many tasks as possible for a high score (Team based, not individual).

#### Tasks
A task is a game object that contains one or more subtasks to be completed by the developer and tester by the tester.

##### Project Manager
Project manager has an undefined amount of tasks to give to the developer and manage for future roles (tester and reviewer).

- Each task is work some amount of points, with tasks containing more subtasks being worth more (not linear)
- PM can see the time estimate to complete all subtasks but not what each individual subtask is (Impression he does not have any technical knowledge)
- PM must manage tasks given to developer and so forth to make sure they do not hit burnout
- Only the PM can see the burnout meters of each player

##### Developer
Developer has several work stations that each can complete a subtask in a task by dragging a task to the work station

- Has a burnout meter dependent on the amount of tasks in his area
- Tasks must be placed at a workstation and only a single task can be present at a single workstation

##### Tester
Tester has several work stations that each can complete a subtask in a task by dragging a task to the work station

- Has a burnout meter dependent on the amount of tasks in his area
- Tasks must be placed at a workstation and only a single task can be present at a single workstation
- Subtask testing will be of an undefined amount of time that can be more or less than the time it took in development
- Total testing time of subtasks in a task will be greater than the time it takes in development to add stress
- After testing all subtasks, can drag the task to the completed section (or to review if added).

#### Game Mechanics
- Burnout: 
    - players will explode into flames when they hit burnout, reseting all tasks being done and putting them into a unplayable state for some amount of time
    - burnout increases constantly and only decreases once a task is moved to the next stage of development, thus more tasks has a snowball affect on the amount of burnout
    
- Random Events
    - just like real life a developer may become sick etc and this will have some discussed in game effects


## Action Items
- [ ] 

## Additional Notes
- [Any additional notes or remarks from the meeting]

## Missing Attendees

### Full Name
- **Reason:** [Reason for missing the meeting]
- **Reallocation:** [What will be done by this team member to compensate]

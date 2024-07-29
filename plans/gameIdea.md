# Dev-a-station
the entire premise of this game is to simulate the overall idea of a kanban board and vosualilsation of workflow, the whole idea of this game is 
that it will be a top down multiplayer game, the layout of this and map will represent that of a kanban board where 
there will be at least 1 player in each column, each player will have a 
diffrent role depending on the column they are in but will have a similar game experience as all the other players
the main goal of this game will be to score as many points in a given time frame, the way players will earn points in a basic idea will be my completion of tickets.
# tickets
The entire point system revolves around tickets, these tickets will appear into the project manager column and will have initial tasks to do
such as, scope, spec etc... (to be decided) once scoped out an OVERALL time estimate will be put on the ticket, this ticket will have developer and tester time estimates aswell 
for the project manger to use (which has a margin of error)
tickets will be passed from column to column as all role tasks are completed, 
like if all developer tasks are completed then it will move to the tester column, if all the testing tasks are done then it will move out of the board and be "merged" awarding points
tickets will have events (a nice to have if we get to it). these events are "blow out" and "bugged", described for the diffrent roles below.

tickets will look different for each role:
- as a project manager
  - will see time estimates of how much each ticket should be in each COLUMN, this estimate makes the assumption that a player is working on each workstation for each task
  - eg: Task: fix nav bar, Dev estimate: 10sec Test estimate: 15sec
- developer:
  - a developer will see 1-3 tasks on the ticket, each with their own time estimates of how long each task must be worked on to be completed
- tester:
  - a tester will see 1-3 related tasks on the tickets, each with their own time estimates of how long each task must be worked on to be completed

# stations 
A station is a game tile where a certain task on a ticket can be completed, when a ticket is placed on the workstation that corresponsing task will slowly be completed (time taken spesific to estimate on ticket)
A ticket on a workstation can be completed much faster if the player interacts with the workstation and "works on it"

# the main roles/players of the game will be:
- Project manager
  - responsible for deciding which and what tickets to pass to the developer
  - responsible for completing research/scoping of tickets (project manger tasks to do at a workstation)
    - research 
    - scoping
    - spec ticket
  - responsibility in managing the burnout of other players by making desicions on the time estimates of tickets
  - deal with blow up tickets (backlog)
- Developer
  - responsible for taking tickets from the project manger and working on set tasks
  - tasks are
    - to be decided but should start with 3
  - while developing a ticket can "blow out" and split into more tasks which will be thrown at the project manager to re adjust priority
  - responsible for completing tickets and passing them to the tester
- Tester
    - responsible for taking tickets from the developer and working on set tasks
    - tasks are
        - to be decided but should start with 3
    - while testing a ticket can have an error found and be thrown back to the developer to repeat that task on that work
    - responsible for completing tasks to finish testing the ticket (working at workstations) and merging the ticket

# Burn out system
The concept of burnout adds a fun mechanism to the game to add a project management factor into the game, where each player (project manger*, developer, tester) will
all have a "Burn out meter" this burnout meter represents the overload of work on the developer and will function as followed
- each player will start the game with a burnout of 0%
- burnout meter will increase proportionally to the amount of tickets there are in each column (eg: if developer has 5 tickets and tester hsa 2 tickets then developer burnout will increase faster then tester)
- burnout will decrease when a ticket moves out of the players column (eg: developer completes a ticket and moves onto tester so his burnout is reduced by 15%*), this is to reward fast work that can be stratergise
- when a player is burnt out (burn out meter hits 100%) they will become fried/blow up (visually shown) where they will not be able to complete or do anything for a set amount of time, after which their burnout meter will be re set to 0
- Visually As a player gets more and more burnt out they will catch fire and the fire will grow

# point system
the point system in the game in a nutshell is as follows "complete tickets from project manager -> developer -> tester" and earn points
the amount of points earned per ticket is influenced by different factors.
- base point worth of a ticket is based on the amount of time that is estimated to complete that ticket (eg: ticket starts at 30point worth)
- if time takes longer to complete ticket, amount of points awarded is reduced, if less time is taken, more points is awarded
- points are reduced for each burnt out player there is (burn out is > then 70% so points = points * 0.8)
- streaks factor could also influence point worth (higher streak then higher multipler)

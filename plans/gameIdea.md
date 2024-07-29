# Dev-a-station

**Dev-a-station** is a multiplayer, top-down game that simulates a Kanban board and visualisation of workflow. 
The game map represents a Kanban board, with at least one player in each column, each having different roles but 
similar experiences. The main goal is to score as many points as possible within a given time frame by completing tickets.

## Tickets
The entire point system revolves around tickets. These tickets start in the project manager column with initial tasks 
such as scoping and specification. Each ticket has an overall time estimate, including developer and tester time 
estimates, which the project manager uses, factoring in a margin of error. Tickets move from column to column as 
tasks are completed. For example, once all developer tasks are completed, the ticket moves to the tester column. 
When testing is finished, the ticket is "merged," awarding points. Tickets may have events like "blow out" and 
"bugged," described for different roles below.

Tickets appear differently to each role:
- **Project Manager:**
  - Sees time estimates for each ticket in each column, assuming one player per workstation per task.
  - Example: Task: Fix nav bar, Dev estimate: 10s, Test estimate: 15s.
- **Developer:**
  - Sees 1-3 tasks per ticket, each with its own time estimate.
- **Tester:**
  - Sees 1-3 related tasks per ticket, each with its own time estimate.

## Stations
A station is a game tile where a task on a ticket can be completed. When a ticket is placed on the workstation, the task is slowly completed according to the time estimate. Players can complete tasks faster by interacting with the workstation.

## Roles

### Project Manager
- Decides which tickets to pass to developers.
- Completes research and scoping tasks at workstations:
  - Research
  - Scoping
  - Specifying tickets
- Manages player burnout by estimating ticket times.
- Handles blow-up tickets (backlog).

### Developer
- Works on tickets from the project manager.
- Completes specified tasks (starting with three).
- Manages blow-out tickets that split into more tasks, requiring project manager reprioritisation.
- Passes completed tickets to the tester.

### Tester
- Works on tickets from the developer.
- Completes specified tasks (starting with three).
- Manages error tickets that need returning to the developer.
- Completes testing tasks to finish and merge tickets.

## Burnout System

The burnout system adds a project management challenge. Each player (project manager, developer, tester) has a burnout meter:
- Starts at 0%.
- Increases with the number of tickets in their column (e.g., if a developer has 5 tickets and a tester has 2, the developer’s burnout increases faster).
- Decreases when a ticket moves out of their column (e.g., moving a ticket to the tester reduces developer burnout by 15%).
- When burnout hits 100%, the player is temporarily incapacitated and visually "fried," resetting their burnout meter to 0 after a set time.
- As burnout increases, the player visually catches fire, which grows as burnout worsens.

## Point System

Points are awarded by completing tickets from project manager to developer to tester. The points system includes:
- Base points based on the estimated time to complete a ticket (e.g., a 30-point ticket).
- Points are reduced if completion time exceeds estimates, and increased if completed faster.
- Points are reduced for each burnt-out player (e.g., if burnout > 70%, points = points * 0.8).
- Streaks can influence points, with higher streaks increasing the multiplier.

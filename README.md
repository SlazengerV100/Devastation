# Devastation

Devastation (like Dev-a-station) is a three-player, top-down game. Players move around a map which simulates a Kanban board and visualises real-life workflows. There is at least one player in each bucket (room) of the Kanban board, with each bucket corresponding to the roles of Project Manager, Developer and Tester. The main goal of the game is to score as many points as possible within a given time frame by completing tickets.

## Features
### Tickets
Spawn randomly in the Project Manager room. Each ticket has a series of tasks which have to be completed at different stations. Each task has an auto-generated estimated time of completion for the Project Manager to use strategically.
### Stations
The game tiles where tasks are completed. Stations are only present in the Developer and Tester rooms. Dropping a ticket on a station will cause the task timer to reduce over time. Once the task timer reaches zero, the task is complete.
### Roles
Determine which room the players are allowed to move in and which stations the players can place tickets on. The Project Manager oversees ticket distribution by strategically passing tickets into the Developer room and manages player burnout and blowout tickets. The Developer(s) complete tasks and pass tickets through to the Tester room. The Tester(s) also complete tasks. Once all the tasks on a ticket are completed, the ticket can be dropped into the completion tile to gain points and increase the team score. If not all tasks are completed, the ticket may need to be passed back to the Developer.
### Work Lifecycle Simulation
Players can pick up and drop tickets, which simulates work moving through the work lifecycle. Each room only has one ‘door’ tile which players from both adjacent rooms can place tickets on. This simulates the forming of bottlenecks.
### Point System
Points are awarded based upon the comparison between the total completion time and the initial auto-generated time estimates.

## Project Objectives

- The aim of this project was to develop a fun and educational web-based multiplayer game for university students to play
- It teaches the basic principles of Agile software development practices
- The game incorporates real-world agile challenges like random events and prioritising tasks

# Running the game
See [RUNNING.md](https://github.com/SlazengerV100/Devastation/blob/main/RUNNING.md)

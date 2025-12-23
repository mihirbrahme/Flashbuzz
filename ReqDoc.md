# Product Requirements Document: FlashBuzz

## 1\. Product Overview

**FlashBuzz** is a real-time, host-led trivia application designed to digitize the experience of a physical game show. It solves the problem of determining "who raised their hand first" in a fair, automated way.

*   **Primary Goal:** Enable a Host (Admin) to run a quiz where Players compete for the right to answer based on reaction time.
    
*   **Key Mechanic:** A "Queue-based" buzzer system where multiple players can buzz in, and the system ranks them (1st, 2nd, 3rd) for the host to verify.
    

* * *

## 2\. User Roles

### A. The Admin (Host)

*   **Persona:** A teacher, team lead, or event organizer.
    
*   **Responsibility:** Controls the pace of the game, reads questions, verifies answers verbally, and manages the scoreboard.
    
*   **Device:** Ideally a tablet or phone (can be projected to a screen).
    

### B. The Player

*   **Persona:** A student, colleague, or party guest.
    
*   **Responsibility:** Reads the question, buzzes in as fast as possible, and answers verbally if they win the buzzer.
    
*   **Device:** Personal smartphone.
    

* * *

## 3\. Functional Feature Set

### 3.1 Admin Features (The Control Room)

1.  **Quiz Import (Google Sheets Integration):**
    
    *   The Admin can create a quiz by simply pasting a link to a published Google Sheet (CSV).
        
    *   The system automatically parses the Question, Points per Question, and Time Limits.
        
    *   Eliminates the need to type questions manually on a mobile device.
        
2.  **Lobby Management:**
    
    *   **Room Code Generation:** Generates a unique 6-character code (e.g., `QUIZ99`) for players to join.
        
    *   **Player List:** Displays real-time list of joined players.
        
    *   **Kick Function:** Ability to remove inappropriate names or unwanted players from the lobby.
        
3.  **Game Flow Control:**
    
    *   **"Next Question" Trigger:** Pushes the current question text to all player devices.
        
    *   **"Open Buzzer" Trigger:** Unlocks the buzzer buttons on all player devices (visual signal change).
        
    *   **"Reset Round" Trigger:** Clears the buzzer queue if no one answers correctly.
        
4.  **Queue & Scoring:**
    
    *   **Live Leaderboard:** Shows the exact order in which players buzzed (1st, 2nd, 3rd).
        
    *   **Answer Validation:**
        
        *   **Correct Button:** Awards the specific points for that question to the player and closes the round.
            
        *   **Wrong Button:** Removes the top player from the queue and prompts the next player (2nd place) to answer.
            
    *   **Score Adjustment:** Ability to manually edit scores if a mistake was made.
        

### 3.2 Player Features (The Buzzer)

1.  **Frictionless Entry:**
    
    *   **No Login Required:** Players join using only the Room Code and a Nickname.
        
    *   **Session Recovery:** If the app is closed and reopened, the player is automatically reconnected to their session/score.
        
2.  **The Buzzer Interface:**
    
    *   **State-Aware Button:** The main button changes appearance based on the game state:
        
        *   _Grey/Disabled:_ "Listen to the Host."
            
        *   _Red/Active:_ "GO!" (Buzzer is open).
            
        *   _Green/Locked:_ "You buzzed! Check your rank."
            
3.  **Real-Time Feedback:**
    
    *   **Queue Position:** Instead of just "You Won" or "You Lost," the player sees "You are #3 in line."
        
    *   **Question Display:** The current question text and point value are visible on the player's screen to ensure clarity.
        
4.  **Scoreboard:**
    
    *   A persistent view of their own current score and rank relative to others.
        

* * *

## 4\. Application Flow (The "Happy Path")

This section details the step-by-step lifecycle of a single quiz session.

### Phase 1: Setup & Lobby

1.  **Admin:** Opens app -> Selects "Create Quiz" -> Pastes Google Sheet Link -> Clicks "Start Lobby."
    
2.  **System:** Generates Room Code (e.g., `TRIVIA`).
    
3.  **Player:** Opens app -> Enters `TRIVIA` and Nickname `Alice`.
    
4.  **Sync:** `Alice` appears on Admin's screen. Admin waits for all to join.
    

### Phase 2: The Question Loop

1.  **Admin:** Taps **"Show Question 1"**.
    
2.  **System:**
    
    *   Sends Question Text ("What is the capital of France?") and Point Value (10pts) to all phones.
        
    *   **Buzzer State:** LOCKED (Grey). Players can read, but cannot buzz.
        
3.  **Admin:** Reads the question aloud (optional). Taps **"OPEN BUZZER"**.
    
4.  **System:**
    
    *   **Buzzer State:** OPEN (Red).
        
    *   A timer (invisible or visible) starts server-side for tie-breaking.
        

### Phase 3: The Race (The Buzzer Queue)

1.  **Action:**
    
    *   Player `Alice` taps buzzer at 1.2 seconds.
        
    *   Player `Bob` taps buzzer at 1.4 seconds.
        
    *   Player `Charlie` taps buzzer at 2.0 seconds.
        
2.  **Feedback:**
    
    *   `Alice` sees: "You are #1! Get ready to answer."
        
    *   `Bob` sees: "You are #2. Stand by."
        
    *   `Charlie` sees: "You are #3."
        
    *   **Admin Screen:** Updates to show a list: 1. Alice, 2. Bob, 3. Charlie.
        

### Phase 4: Validation & Scoring

1.  **Verification:** Admin looks at screen, sees Alice is #1. Admin asks Alice: "What is your answer?"
    
2.  **Scenario A (Correct Answer):**
    
    *   Alice says "Paris."
        
    *   Admin taps **"Correct"** next to Alice's name.
        
    *   **System:** Adds 10 points to Alice. Clears the queue. Moves to Question 2 state.
        
3.  **Scenario B (Wrong Answer):**
    
    *   Alice says "London."
        
    *   Admin taps **"Wrong"** next to Alice's name.
        
    *   **System:** Removes Alice from the queue. Sends notification to Bob: "You are now #1!"
        
    *   Admin asks Bob for the answer.
        

### Phase 5: Endgame

1.  **Completion:** After the final question, the Admin taps "End Quiz."
    
2.  **Podium:** A final leaderboard is displayed on all devices showing the Top 3 players and their final scores.
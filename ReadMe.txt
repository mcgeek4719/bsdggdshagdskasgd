ReadMe zum Game-Of-Life
    => Anforderungen
        Java 21+
        Maven

    => Start des Programmes
        1. Öffne die GameOfLifeApp.java Datei
        2. Führe jene Datei aus

    => Bedienungsanleitung
        Zellen können angeclickt werden
            -> Beim clicken kann man gedrückt halten
        Start - startet die Simulation
        Pause - Pausiert die Simulation
        Reset - Setzt das Spiel zurück
        Speed-Slider - Reguliert das Tempo (5ms-550ms, Startwert 500ms)
        Pattern-Selector - Wählt eines der Muster aus
                -> None: Freies Zeichnen
                -> Rakete: eine klassische Game Of Life Rakete
                -> Stern: Setzt einen Stern
                -> Propeller: Setzt einen Propeller

    => kurze Beschreibung:
        - GameOfLifeApp:
            Heartfile des Programmes, baut das Fenster auf und führt die Simulation aus
        - GameOfLifeGrid:
            Verwaltet die Logik, baut dabei das Array-Feld auf, beartbeitet dieses und berechnet die nächste Simulation
        - GameOfLifeView:
            Setzt die Änderungen der GameOfLifeGrid visuell im Fenster um.
        - StartPattern:
            Definiert die Pattern fürs Zeichnen und Laden von Mustern.

    => Bemerkungen:
        - Manche Logikelemente wurden modifiziert von meinem StarBattleGo Programm des ersten Semesters übernommen.

    => Nutzung von KI:
        - Claude 4.6 wurde verwendet, um die Logik für das Mouseclickdragging - Methode "handleMousDragged" zu entwerfen, welche dann von mir an mein Programm angepasst wurde


GitHub Link:
https://github.com/mcgeek4719/game-of-life-prog2

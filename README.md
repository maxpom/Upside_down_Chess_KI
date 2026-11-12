# Upside-Down Chess KI ♟️

Eine Schach-KI in Java für die Variante **Upside-Down Chess**, entwickelt im KI-Projekt an der TU Berlin (SoSe 2021). Die KI trat in Contests gegen die anderer Teams an.

Bei Upside-Down Chess ist die Startaufstellung vertauscht: Beide Seiten beginnen auf der gegnerischen Brettseite, die Bauern stehen vor den Figuren. Bauern können nur schlagend umwandeln.

## Features

- **Bitboards** für schnelle Zuggenerierung
- **Alpha-Beta-Suche** (Principal Variation Search) mit **Iterative Deepening**
- **Transpositionstabelle** mit Zobrist-Hashing
- **Bewertungsfunktion** aus Material, Figurenpositionen und Mobilität
- **Eröffnungsbuch** für die ersten drei Züge
- **Evolutionärer Algorithmus**, der die Figurenwerte durch Selbstspiel optimiert hat

## Starten

Voraussetzung: Java (JDK 11+)

```bash
cd Upside_Down_Chess
javac -d build $(find src -name '*.java' -not -path '*JUnitTests*')

# Selbst gegen die KI spielen
java -cp build UpsideDownChessBB.GameAgainstKI

# Contest-Modus (liest eine FEN-Stellung, gibt einen Zug aus)
java -cp build UpsideDownChessBB.PlayGame
```

Alternativ liegt unter `out/artifacts/UpsideDownChess2_jar/` eine fertige JAR mit Startskript.

## Projektstruktur

```
src/UpsideDownChessBB/
├── PlayGame.java        # Contest-Modus
├── GameAgainstKI.java   # Mensch gegen KI
├── BoardGeneration.java # Brett, FEN-Import, Zeitmanagement
├── PiecesAndMoves/      # Zuggenerierung
├── Search/              # Suche, Bewertung, Transpositionstabelle
└── Evolution/           # Genetischer Algorithmus
```

## Quellen

Der Aufbau der Bitboards orientiert sich an der YouTube-Reihe [Bitboards in Java von „Logic Crazy Chess“](https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c).

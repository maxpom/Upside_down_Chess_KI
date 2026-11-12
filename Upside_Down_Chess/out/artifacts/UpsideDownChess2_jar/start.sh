#!/bin/sh
cd "$(dirname "$0")" || exit

# $1 player is w or b
# $2 totalTime as double
# $3 maxMoves as int

#
# TODO:
# *** start your ai here ***
#
java -jar ./UpsideDownChess2.jar $1 $2 $3

# python example:
# python3 ./some/dir/ai.py $1 $2 $3

#!/usr/bin/env bash

__DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
FONT_LIST=`cat $__DIR/fonts-list`
FONT_ENDPOINT="http://www.figlet.org/fonts"

for i in $FONT_LIST; do
    curl "$FONT_ENDPOINT/$i.flf" > $__DIR/fonts/$i.flf
done

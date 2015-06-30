#!/bin/bash

make -f makefile.server alldb
make -f makefile.server createdomain
make -f makefile.server startall
make -f makefile.client deploy

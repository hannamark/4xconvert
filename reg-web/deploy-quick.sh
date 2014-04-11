#!/bin/bash
JBOSS_HOME=/Users/vinodh/apps/pa/paear/jboss-4.0.5.GA-jems-ejb3
export JBOSS_HOME
ant deploy-notest -Djboss.deploy.directory=$JBOSS_HOME/server/paear/deploy
#!/bin/sh
set -e

#export CF_TRACE=true
export GIT_URI="https://github.com/mborges-pivotal/pcf-ers-recipes"

cf create-service p-service-registry standard service-registry
cf create-service -c '{ "git": { "uri": "'"$GIT_URI"'", "label": "master" } }' p-config-server standard config-server

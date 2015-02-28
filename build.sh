#!/bin/bash

echo_blue(){ echo -e "\033[0;36m$*\033[0m"; }

show_usage(){
  echo 'Usage: '$0' [test|package|install|deploy]'
  echo
  exit 1
}

check_stepup_utility(){
  if ! type stepup &> /dev/null; then
    echo 'StepUp command-line utility not found, please install it to continue'
    echo
    echo '  $ sudo gem install step-up'
    echo
    exit 1
  fi
}

die() {
  echo $1 && exit 1
}

set_project_version(){
  mvn versions:set -DnewVersion="$1" --quiet
}

build(){
  check_stepup_utility
  local VERSION=`stepup --format mvn-snapshot`
  local PROPS='-Dprop.engine.version.describe='$(git describe 2>/dev/null)
  local propfile=".mvn$1"

  if [ -f $propfile ]; then
    if echo $VERSION | grep SNAPSHOT &> /dev/null; then
      PROPS=$PROPS' '$(cat $propfile | grep -v '#release' | cut -d'#' -f1 | xargs echo)
    else
      PROPS=$PROPS' '$(cat $propfile | grep -v '#snapshot' | cut -d'#' -f1 | xargs echo)
    fi
  fi

  echo
  echo "+ Building all modules with version \"$VERSION\""
  echo '----------'
  echo_blue mvn clean $1 $PROPS
  sleep .5
  set_project_version $VERSION
  mvn clean $1 $PROPS && mvn versions:revert --quiet && git checkout -- pom.xml
  # find . -name \*.diff | xargs rm -f
}

case $1 in
  test|package|install|deploy)
    build $1
    ;;
  *)
    show_usage
    ;;
esac


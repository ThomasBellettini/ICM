REPO_URL="git@github.com:ThomasBellettini/ICM.git"
PREFIX="[ICM] "

BUILD_CMD="javac "
DIR_TMP="~/"

if [[ "$1" == "pvpbox" ]]
then
	echo "${PREFIX} Launching session for getting pvpbox plugin ..."
	echo "${PREFIX} Cloning repository ..."
	git clone ${REPO_URL} -b pvpbox ${DIR}
	
elif [[ "$1" == "faction" ]]
then
	echo "${PREFIX} Launching session for getting faction plugin ..."
else
	echo "${PREFIX} ICM Built Script:"
	echo "          ./make.sh <server> => <server> can be replaced by \"faction\" or \"pvpbox\""
fi

gradle clean bootJar
echo Build Successfully
scp -r ./build/libs/mychat.jar itsusinn@atri:/home/itsusinn/
echo Copy Successfully

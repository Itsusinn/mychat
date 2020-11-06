package routes

import (
	"github.com/emicklei/go-restful"
	log "github.com/sirupsen/logrus"
	"mychat/service"
)

func (u UserResource) RegisterAuthService(container *restful.Container) *restful.WebService {
	ws := new(restful.WebService)
	defer container.Add(ws)
	//configure root path of comment service
	ws.
		Consumes(restful.MIME_JSON).
		Produces(restful.MIME_JSON)

	ws.POST("/login").To(u.login)
	return ws
}
func (u UserResource) login(request *restful.Request, response *restful.Response) {
	//POJO
	loginRequest := LoginRequest{}

	err := request.ReadEntity(&loginRequest)
	if err != nil {
		log.WithField("LoginRequest", loginRequest).Error("LoginRequest Decode Failed")
		response.WriteErrorString(400, "LoginRequest Decode Failed")
		return
	}
	service.Login(loginRequest.account, loginRequest.password)
}

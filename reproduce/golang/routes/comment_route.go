package routes

import (
	"github.com/emicklei/go-restful"
)

func (u UserResource) RegisterCommentService(container *restful.Container) *restful.WebService {
	ws := new(restful.WebService)
	defer container.Add(ws)
	//configure root path of comment service
	ws.
		Path("/comment").
		Consumes(restful.MIME_JSON).
		Produces(restful.MIME_JSON)

	ws.GET("/add")
	return ws
}

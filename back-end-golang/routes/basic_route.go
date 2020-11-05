package routes

import (
	"github.com/emicklei/go-restful"
	"io"
	"net/http"
)

type UserResource struct{}

func (u UserResource) RegisterBasicService(container *restful.Container) {
	ws := new(restful.WebService)
	//设置匹配的schema和路径
	ws.Path("/user").Consumes(restful.MIME_JSON).Produces(restful.MIME_JSON)

	//设置不同method对应的方法，参数以及参数描述和类型
	//参数:分为路径上的参数,query层面的参数,Header中的参数
	ws.Route(ws.GET("/{id}").
		To(u.result).
		Doc("方法描述：获取用户").
		Param(ws.PathParameter("id", "参数描述:用户ID").DataType("string")).
		Param(ws.QueryParameter("name", "用户名称").DataType("string")).
		Param(ws.HeaderParameter("token", "访问令牌").DataType("string")).
		Do(returns200, returns500))
	ws.Route(ws.POST("").To(u.result))
	ws.Route(ws.PUT("/{id}").To(u.result))
	ws.Route(ws.DELETE("/{id}").To(u.result))
	ws.Route(ws.GET("/test").To(u.test))

	container.Add(ws)
}

func (UserResource) SwaggerDoc() map[string]string {
	return map[string]string{
		"":         "Address doc", //空表示结构本省的描述
		"country":  "Country doc",
		"postcode": "PostCode doc",
	}
}

//where the main logic locates in
func (u UserResource) result(request *restful.Request, response *restful.Response) {
	io.WriteString(response.ResponseWriter, "this would be a normal response")
}

func (u UserResource) test(request *restful.Request, response *restful.Response) {
	io.WriteString(response.ResponseWriter, "Hello World")
}

func returns200(b *restful.RouteBuilder) {
	b.Returns(http.StatusOK, "OK", "success")
}

func returns500(b *restful.RouteBuilder) {
	b.Returns(http.StatusInternalServerError, "Bummer, something went wrong", nil)
}

package main

import (
    "github.com/emicklei/go-restful"
    swagger "github.com/emicklei/go-restful-swagger12"
    log "github.com/sirupsen/logrus"
    "io"
    "net/http"
    "os"
    "strconv"
)

func init() {
    // 设置日志格式为json格式
    log.SetFormatter(&log.JSONFormatter{})

    // 设置将日志输出到标准输出（默认的输出为stderr，标准错误）
    // 日志消息输出可以是任意的io.writer类型
    log.SetOutput(os.Stdout)

    // 设置日志级别为warn以上
    log.SetLevel(log.DebugLevel)
}

func main(){
    wsContainer := restful.NewContainer()

    // 跨域过滤器
    cors := restful.CrossOriginResourceSharing{
        ExposeHeaders:  []string{"X-My-Header"},
        AllowedHeaders: []string{"Content-Type", "Accept"},
        AllowedMethods: []string{"GET", "POST"},
        CookiesAllowed: false,
        Container:      wsContainer}
    wsContainer.Filter(cors.Filter)

    // Add container filter to respond to OPTIONS
    wsContainer.Filter(wsContainer.OPTIONSFilter)


    //swagger's config
    config := swagger.Config{
        WebServices:    restful.DefaultContainer.RegisteredWebServices(), // you control what services are visible
        WebServicesUrl: "http://localhost:7342",
        ApiPath:        "/apidocs.json",
        ApiVersion:     "V1.0",
        // Optionally, specify where the UI is located
        SwaggerPath:     "/apidocs/",
        SwaggerFilePath: "/dist"}
    swagger.RegisterSwaggerService(config, wsContainer)
    //swagger.InstallSwaggerService(config)

    //register a custom user resource
    u := UserResource{}
    u.RegisterTo(wsContainer)

    port := strconv.Itoa(serverConfig.Port)
    log.Info("start listening on localhost:",port)

    server := &http.Server{Addr: ":"+port, Handler: wsContainer}
    defer server.Close()
    log.Error(server.ListenAndServe())
}



type UserResource struct{}

func (u UserResource) RegisterTo(container *restful.Container) {
    ws := new(restful.WebService)
    //设置匹配的schema和路径
    ws.Path("/user").Consumes("*/*").Produces("*/*")

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
        "":         "Address doc",//空表示结构本省的描述
        "country":  "Country doc",
        "postcode": "PostCode doc",
    }
}
//where the main logic locates in
func (u UserResource) result(request *restful.Request, response *restful.Response) {
    io.WriteString(response.ResponseWriter, "this would be a normal response")
}

func (u UserResource) test(request *restful.Request, response *restful.Response){
    io.WriteString(response.ResponseWriter,"Hello World")
}

func returns200(b *restful.RouteBuilder) {
    b.Returns(http.StatusOK, "OK", "success")
}

func returns500(b *restful.RouteBuilder) {
    b.Returns(http.StatusInternalServerError, "Bummer, something went wrong", nil)
}
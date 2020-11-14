package main

import (
	"github.com/emicklei/go-restful"
	swagger "github.com/emicklei/go-restful-swagger12"
	log "github.com/sirupsen/logrus"
	config2 "mychat/config"
	"mychat/routes"
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

func main() {
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
	u := routes.UserResource{}
	//
	u.RegisterBasicService(wsContainer)
	u.RegisterCommentService(wsContainer)

	port := strconv.Itoa(config2.AppConfig.Server.Port)
	log.Info("start listening on localhost:", port)

	//indeed start a http server
	server := &http.Server{Addr: ":" + port, Handler: wsContainer}

	defer server.Close()
	log.Error(server.ListenAndServe())
}

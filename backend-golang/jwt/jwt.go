package jwt

import (
	jwtmiddleware "github.com/auth0/go-jwt-middleware"
	"github.com/dgrijalva/jwt-go"
	"github.com/emicklei/go-restful"
	log "github.com/sirupsen/logrus"
	"mychat/config"
)

var (
	sharedSecret  = config.AppConfig.JWT.Secret
	jwtMiddleware = jwtmiddleware.New(jwtmiddleware.Options{
		ValidationKeyGetter: func(token *jwt.Token) (interface{}, error) {
			return sharedSecret, nil
		},
		SigningMethod: jwt.SigningMethodHS256,
	})
)

/**
A LifeTime Hook Filter Method
Every call through this route will be handled vy this method
*/
func AuthJWT(req *restful.Request, resp *restful.Response, chain *restful.FilterChain) {

	if err := jwtMiddleware.CheckJWT(resp.ResponseWriter, req.Request); err != nil {
		log.Error("Authentication error: %v", err)
		resp.WriteErrorString(401, "Not Authorized")
		return
	}
	chain.ProcessFilter(req, resp)
}

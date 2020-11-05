package jwt

import (
	jwtmiddleware "github.com/auth0/go-jwt-middleware"
	"github.com/dgrijalva/jwt-go"
	"github.com/emicklei/go-restful"
	log "github.com/sirupsen/logrus"
)

// This example shows how to create a (Route) Filter that performs a JWT HS512 authentication.
//
// GET http://localhost:8080/secret
// and use shared-token as a shared secret.

var (
	sharedSecret  = []byte("shared-token")
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
		_ = resp.WriteErrorString(401, "401: Not Authorized")
		return
	}
	chain.ProcessFilter(req, resp)
}

package jwt

import (
	"errors"
	. "github.com/dgrijalva/jwt-go"
	"github.com/emicklei/go-restful"
	"mychat/config"
)

type Principal struct {
	UID  int64
	UUID string
}

func ParsePrincipal(request *restful.Request) (Principal, error) {
	user := request.Request.Context().Value("user")
	var principal Principal
	for k, v := range user.(Token).Claims.(MapClaims) {
		if k == "UID" {
			principal.UID = v.(int64)
		}
		if k == "UUID" {
			principal.UUID = v.(string)
		}
	}
	if len(principal.UUID) == 0 || principal.UID == 0 {
		return principal, errors.New("Parse Failed\n")
	}
	return principal, nil
}

func SignToken(UID int64, UUID string) (string, error) {
	token := NewWithClaims(SigningMethodHS256, MapClaims{
		"UID":    UID,
		"UUID":   UUID,
		"Issuer": "MyChat",
	})
	tokenString, err := token.SignedString(config.AppConfig.JWT.Secret)
	return tokenString, err
}

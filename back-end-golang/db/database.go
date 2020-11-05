package db

import (
	_ "github.com/go-sql-driver/mysql"
	log "github.com/sirupsen/logrus"
	"mychat/config"
	"os"
)
import "xorm.io/xorm"

func init() {
	address := config.AppConfig.DataBase.Address
	sqlType := config.AppConfig.DataBase.Type
	user := config.AppConfig.DataBase.User
	password := config.AppConfig.DataBase.Password
	if sqlType != "mysql" {
		log.WithField("DataBaseType", sqlType).Error("Unsupported DataBase Type")
		os.Exit(1)
	}
	source := sqlType + "//" + user + ":" + password + "@" + address + "?sslmode=disable&charset=utf8"
	//"postgres", "postgres://postgres:root@localhost:5432/test?"
	engine, err := xorm.NewEngine(sqlType, source)

	if err != nil || engine == nil {
		log.WithField("Source", source).Error("Engine Create Failed")
		os.Exit(1)
	}

	err = engine.Ping()
	if err != nil {
		log.WithField("Source", source).Error("DataBase Ping Failed")
		os.Exit(1)
	}

}

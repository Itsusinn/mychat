package db

import (
	"errors"
	_ "github.com/go-sql-driver/mysql"
	log "github.com/sirupsen/logrus"
	"mychat/config"
	"mychat/util"
	"os"
	"xorm.io/xorm"
	"xorm.io/xorm/names"
)

var supportedDatabases = []string{"mysql", "more"}

var Beans = []interface{}{Post{}, Comment{}, User{}}

var Database *xorm.Engine

func init() {
	var err error
	database := config.AppConfig.DataBase
	Database, err = newEngine(&database)
	if err != nil {
		log.Error("Database Load Failed")
		os.Exit(1)
	}
}
func newEngine(config *config.DataBase) (*xorm.Engine, error) {
	var err error

	//get configure
	address := config.Address
	sqlType := config.Type
	user := config.User
	password := config.Password

	// if it is not supported database
	if util.Contains(sqlType, supportedDatabases) {
		log.WithField("DataBaseType", sqlType).Error("Unsupported DataBase Type")
		return nil, err
	}

	source := sqlType + "//" + user + ":" + password + "@" + address + "?sslmode=disable&charset=utf8"

	engine, err := xorm.NewEngine(sqlType, source)
	if err != nil || engine == nil {
		log.WithField("Source", source).Error("Engine Create Failed")
		return nil, err
	}
	if err = engine.Ping(); err != nil {
		log.WithField("Source", source).Error("DataBase Ping Failed")
		return nil, err
	}
	//set a reflect mapper
	engine.SetMapper(names.GonicMapper{})

	success := syncTables(engine, Beans)
	if !success {
		return nil, errors.New("Failed to Sync All Tables")
	}

	return engine, nil
}

func syncTables(engine *xorm.Engine, beans []interface{}) (success bool) {
	for bean := range beans {
		if err := syncTable(engine, bean); err != nil {
			return false
		}
	}
	return true
}

func syncTable(engine *xorm.Engine, bean interface{}) error {
	var err error

	err = engine.Sync2(bean)
	if err != nil {
		log.WithError(err).Error("Failed to Sync Table")
		return err
	}
	return nil
}

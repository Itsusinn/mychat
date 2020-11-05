package db

import (
	_ "github.com/go-sql-driver/mysql"
	log "github.com/sirupsen/logrus"
	"mychat/config"
	"mychat/util"
	"os"
	"xorm.io/xorm"
	"xorm.io/xorm/names"
)

var supportedDatabases = []string{"mysql", "more"}

var Database *xorm.Engine

func init() {
	database := config.AppConfig.DataBase
	//get configure
	address := database.Address
	sqlType := database.Type
	user := database.User
	password := database.Password

	var err error
	// if it is not supported database
	if util.Contains(sqlType, supportedDatabases) {
		log.WithField("DataBaseType", sqlType).Error("Unsupported DataBase Type")
		os.Exit(1)
	}

	source :=
		sqlType + "//" + user + ":" + password + "@" + address + "?sslmode=disable&charset=utf8"

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
	//set a reflect mapper
	engine.SetMapper(names.GonicMapper{})
	//set database globally
	Database = engine
	//sync table data
	err = syncTable()
	if err != nil {
		os.Exit(1)
	}

}

func syncTable() error {
	var err error
	err = Database.Sync2(new(Comment))
	if err != nil {
		log.Error("Failed to Sync Table", err)
		return err
	}
	err = Database.Sync2(new(Post))
	if err != nil {
		log.Error("Failed to Sync Table", err)
		return err
	}
	err = Database.Sync2(new(User))
	if err != nil {
		log.Error("Failed to Sync Table", err)
		return err
	}
	return nil
}

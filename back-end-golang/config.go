package main

import "github.com/koding/multiconfig"
import  "log"

var (
	//a series of global config
	appConfig *AppConfig
	serverConfig  = &appConfig.Server
	databaseConfig = &appConfig.DataBase
	redisConfig = &appConfig.Redis
)

func init() {

	m := multiconfig.NewWithPath("config.yaml") // supports TOML, JSON and YAML

	// Get an empty struct for your configuration
	conf := new(AppConfig)

	// Populated the serverConf struct
	err := m.Load(conf) // Check for error
	if err != nil {
		log.Panic(err)
	}
	m.MustLoad(conf)    // Panic's if there is any error

	appConfig = conf
}

type AppConfig struct {
	Server Server
	DataBase DataBase
	Redis Redis
}
type Server struct {
	Port int `default:"7432"`
}
type DataBase struct {
	Type string `required:"true"`
	Address string `required:"true"`
	User string `default:"root"`
	Password string `required:"true"`
}
type Redis struct {
	Address string `required:"true" default:"127.0.0.1:6379"`
	Password string `default:""`
}
type Log struct {
	Level string `default:"INFO"`
}

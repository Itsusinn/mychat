package config

import (
	"github.com/koding/multiconfig"
	"golang.org/x/net/context"
)
import "log"

var (
	Ctx = context.Background()
	//a series of global config
	AppConfig *Config
	//serverConfig  = &AppConfig.Server
	//databaseConfig = &AppConfig.DataBase
	//redisConfig = &AppConfig.Redis
)

func init() {

	m := multiconfig.NewWithPath("config.yaml") // supports TOML, JSON and YAML

	// Get an empty struct for your configuration
	conf := new(Config)

	// Populated the serverConf struct
	err := m.Load(conf) // Check for error
	if err != nil {
		log.Panic(err)
	}
	m.MustLoad(conf) // Panic's if there is any error

	AppConfig = conf
}

type Config struct {
	Server   Server
	DataBase DataBase
	Redis    Redis
}
type Server struct {
	Port int `default:"7432"`
}
type DataBase struct {
	Type     string `required:"true" default:"mysql"`
	Address  string `required:"true" default:"127.0.0.1:3306/mychat"`
	User     string `required:"true" default:"root"`
	Password string `required:"true" default:"root"`
}
type Redis struct {
	Address  string `required:"true" default:"127.0.0.1:6379"`
	Password string `default:""`
}
type Log struct {
	Level string `default:"INFO"`
}

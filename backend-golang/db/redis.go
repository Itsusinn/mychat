package db

import (
	"github.com/go-redis/redis/v8"
	log "github.com/sirupsen/logrus"
	"mychat/config"
	"os"
)

var RedisClient *redis.Client

func init() {
	rdb := redis.NewClient(&redis.Options{
		Addr:     config.AppConfig.Redis.Address,
		Password: config.AppConfig.Redis.Password,
		DB:       0,
	})

	RedisClient = rdb

	pong, err := rdb.Ping(config.Ctx).Result()

	log.WithFields(log.Fields{
		"redis": config.AppConfig.Redis.Address,
	}).Debug(pong, err)

	//when redis cannot be connected
	if err != nil {
		os.Exit(0)
	}
}

package service

import "mychat/db"

func CheckPassword(account string, password string) {

}

func findUserByAccount(account string) (user db.User, success bool) {
	user = db.User{Account: account}
	_, err := db.Database.Get(&user)
	if err != nil {
		return user, false
	}
	return user, true
}

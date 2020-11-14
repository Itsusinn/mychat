package moe.itsusinn.mychat.models.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class UserRegisterRequest(
    @ApiModelProperty(value = "用户名")
    val username: String,
    @ApiModelProperty(value = "密码")
    var password: String
)
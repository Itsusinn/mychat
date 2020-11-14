package moe.itsusinn.mychat.models.respond

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "用户登陆响应类")
data class UserLoginRespond(
    @ApiModelProperty(value = "状态", example = "Failed||Success")
    val status: String,
    @ApiModelProperty(value = "token", example = "null||token")
    val token: String
)
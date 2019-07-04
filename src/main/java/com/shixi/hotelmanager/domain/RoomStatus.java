package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_room_status")
public class RoomStatus extends Model<RoomStatus> {

    private int id;
    private int roomId;
    @Pattern(regexp = "dddd-dd-dd", message="传入的日期必须是yyyy-MM-dd形式，含前导0")
    private Date recordForDate;
    private int roomNum;//房间编号
}

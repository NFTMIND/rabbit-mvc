package os.rabbit.test;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

	public static void main(String[] args) {
	//	System.out.println(URLDecoder.decode("langtype=0&amp;urls=%7B%22webroot%22%3A%22https%3A%2F%2Fhttptwtexas01.boyaagame.com%2Ftexas%2F%22%2C%22gameurl%22%3A%22https%3A%2F%2Fapps.facebook.com%2Ftwtexas%2F%22%2C%22introurl%22%3A%22https%3A%2F%2Fapps.facebook.com%2Ftwtexas%2F%22%2C%22dataurl%22%3A%22https%3A%2F%2Fbycdn5-i.akamaihd.net%2Fdata%2Fth%2F0304%2F%22%2C%22faqurl%22%3A%22https%3A%2F%2Fapps.facebook.com%2Ftwtexas%2Ffeednew.php%3Fref%3Dsubmit04.php%3Fctid%3D58%22%2C%22logurl%22%3A%22https%3A%2F%2F175.45.32.163%2F%22%2C%22iphoneurl%22%3A%22https%3A%2F%2Fitunes.apple.com%2Fhk%2Fapp%2Fid442870561%3Fmt%3D8%22%2C%22payurl%22%3A%22%22%2C%22cdnurl%22%3A%22https%3A%2F%2Fbycdn5-i.akamaihd.net%2Fimages%2F%22%7D&amp;gateway=https%3A%2F%2Flpftpk01.boyaagame.com%2Ftexas%2Fapi%2Fgateway.php&amp;gateway2=https%3A%2F%2Fhttptwtexas01.boyaagame.com%2Ftexas%2Fapi%2Fgateway.php&amp;flashver=%7B%22miniLoading%22%3A%220227101295151%22%2C%22TexasMain%22%3A%220227101295152%22%2C%22langdata_th%22%3A%220303161306316%22%2C%22HallDialog%22%3A%220214151249743%22%2C%22RoomDialog%22%3A%220220091268931%22%2C%22PublicDialog%22%3A%220304191309544%22%2C%22QuickHelp%22%3A%221218111106858%22%2C%22activeClip%22%3A%220108181170092%22%2C%22expression%22%3A%22070816578741%22%2C%22loadingUI%22%3A%220122141209060%22%2C%22propsMagic%22%3A%22082816011%22%2C%22publicUI%22%3A%220122161210445%22%2C%22roomAnimation%22%3A%22082816013%22%2C%22glodrushinfoPanel0%22%3A%22030616249997%22%2C%22glodrushinfoPanel1%22%3A%2209251637060%22%2C%22glodrushinfoPanel2%22%3A%22081311729900%22%2C%22ChatModule%22%3A%220114111187218%22%2C%22HallFriendsModule%22%3A%220303111303943%22%2C%22HallBackModule%22%3A%22120417129944%22%2C%22HallToolModule%22%3A%22092610880312%22%2C%22InfoBarModule%22%3A%220124101217249%22%2C%22LittleTipsModule%22%3A%220830133105%22%2C%22LookListModule%22%3A%2210300970570%22%2C%22NetConnectMoudle%22%3A%220227101295194%22%2C%22NRoomListModule%22%3A%220109141172029%22%2C%22NTableModule%22%3A%220303121304618%22%2C%22RankModule%22%3A%220113101178878%22%2C%22RoomActivityModule%22%3A%220228181302920%22%2C%22RoomBackModule%22%3A%220122151209500%22%2C%22RoomFriendModule%22%3A%221120111010779%22%2C%22RoomPlayTipModule%22%3A%220114101185823%22%2C%22SelfModule%22%3A%220228111300141%22%2C%22SoundSetModule%22%3A%22121914158854%22%2C%22SystemModuleBase%22%3A%22122617169849%22%2C%22soundModule%22%3A%22122409165917%22%2C%22VotePanelModule%22%3A%22082909316%22%2C%22AswingDll%22%3A%220304181309221%22%2C%22InfoBarModuleUI-0%22%3A%220113171183123%22%2C%22NTableModuleUI-0%22%3A%220115111190913%22%2C%22NRoomListModuleUI-0%22%3A%220113171183206%22%2C%22RoomBackModuleUI-0%22%3A%220113171183126%22%2C%22SelfModuleUI-0%22%3A%220113171183127%22%2C%22RoomActivityModuleUI-0%22%3A%220303101303225%22%2C%22HallFriendsModuleUI-0%22%3A%220113171183129%22%2C%22HallToolModuleUI-0%22%3A%220113171183130%22%2C%22ChatModuleUI-0%22%3A%220113171183131%22%2C%22RoomPlayTipModuleUI-0%22%3A%220114091185201%22%2C%22RoomFriendModuleUI-0%22%3A%22101711919358%22%2C%22RankModuleUI-0%22%3A%220113171183134%22%2C%22NetConnectMoudleUI-0%22%3A%220122141209076%22%2C%22HallDialogUI-0%22%3A%220303111303931%22%2C%22PublicDialogUI-0%22%3A%220303161306189%22%2C%22QuickHelpUI-0%22%3A%221120141012208%22%2C%22RoomDialogUI-0%22%3A%220123101214687%22%2C%22RoomLittleModuleUI-0%22%3A%220114101186585%22%2C%22glodrushinfoPanel3%22%3A%22082909294%22%2C%22glodrushinfoPanel4%22%3A%22082909295%22%2C%22glodrushinfoPanel5%22%3A%22082909296%22%2C%22RoomLittleModule%22%3A%220122161210648%22%2C%22FuncProject%22%3A%220304141307991%22%2C%22MoodsFuncUI-0%22%3A%220115091190181%22%2C%22BonusUI-0%22%3A%220122171211184%22%2C%22LittleGameUI-0%22%3A%220227091294848%22%2C%22AIFuncUI-0%22%3A%220115091190184%22%2C%22ChangeDealerFuncUI-0%22%3A%220115091190185%22%2C%22GrabCardFuncUI-0%22%3A%220115091190186%22%2C%22QuickAddFriendFuncUI-0%22%3A%220115091190187%22%2C%22SayHelloFuncUI-0%22%3A%220115091190188%22%2C%22SendPropsToD%22%3A%220115091190189%22%2C%22SendWelfareFuncUI-0%22%3A%220115091190190%22%2C%22SimpleFuncUI-0%22%3A%220124141217818%22%2C%22SupplyChipsFuncUI-0%22%3A%220115091190192%22%2C%22InvitationUI-0%22%3A%220122141209117%22%2C%22NewPropsFuncUI-0%22%3A%220115091190194%22%2C%22TexasVipUI-0%22%3A%220116151196373%22%2C%22BountyUI-0%22%3A%220123091214062%22%2C%22InsureUI-0%22%3A%220226151292867%22%2C%22BonusUI-th%22%3A%220122171211741%22%2C%22NTableModuleUI-1%22%3A%220115111190967%22%2C%22RoomPlayTipModuleUI-1%22%3A%220113171183179%22%2C%22ChatModuleUI-1%22%3A%220115111190969%22%2C%22RankModuleUI-1%22%3A%220113171183181%22%2C%22RoomBackModuleUI-1%22%3A%220113171183182%22%2C%22SelfModuleUI-1%22%3A%220226101290375%22%2C%22InfoBarModuleUI-1%22%3A%220113171183185%22%2C%22HallToolModuleUI-1%22%3A%220113171183186%22%2C%22HallFriendsModuleUI-1%22%3A%220303111303985%22%2C%22NRoomListModuleUI-1%22%3A%220113171183188%22%2C%22NetConnectMoudleUI-1%22%3A%220123091213991%22%2C%22TexasMain~%22%3A%220227101295152%22%2C%22xml%22%3A%2214030317%22%7D&amp;showtype=0&amp;hidesamecity=1&amp;screen_width=760&amp;screen_height=590&amp;rank_height=185&amp;rank_y=590&amp;rankpic=1&amp;iphoneurl=&amp;verifyurl=&amp;enterRoomLimit=1&amp;openrankhomepage=1&amp;canvas_h=1200&amp;canvas_w=760&amp;twServPath=https%3A%2F%2Fhttptwtexas01.boyaagame.com%2Ftexas%2Fapi%2Fgateway.php&amp;openNewUI=0&amp;loadingCfg=%5B%22loadCfg%5C%2Fth%5C%2Floading15.swf%22%5D&amp;openHello=1&amp;openBonusTabTips=0&amp;appId=158243717529&amp;mid=1553803&amp;sid=13&amp;unid=0&amp;mtkey=jQn3HcKmNQ7ik3hehZuMgGGAY175hP&amp;tid=0&amp;selectarea=0%2C0&amp;firstlogin=0&amp;hd=0&amp;levelLimit=0&amp;ishappyelement=0&amp;product=1&amp;vkey=753c848a36a64a6b5dc919f1de65c678&amp;time=1394022882&amp;flashurl_vars=0&amp;flashurl=https%3A%2F%2Fbycdn5-i.akamaihd.net%2Fcore%2F"));

	
	}

}

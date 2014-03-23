TK.PerMoney = 2; (function(b, a) {
    if (typeof TK.Bet === "undefined") {
        TK.Bet = new(function() {})()
    }
})(window);
TK.Bet.BetMethod = {常规选号: 1,
    文件选号: 2,
    胆拖选号: 3,
    走势选号: 4,
    和值选号: 5,
    随机选号: 6,
    随心所欲: 7,
    首页机选: 8,
    专家推荐: 9,
    频道机选: 10,
    命中注定: 11,
    我的选号: 12,
    活动选号: 13,
    秘书选号: 14
};
TK.Bet.TicketStatus = {待出票: 1,
    正在发送: 2,
    发送成功: 3,
    购买成功: 4,
    出票失败: 5,
    用户撤单: 6,
    系统撤单: 7,
    中奖撤单: 8,
    出号撤单: 9,
    限号撤单: 10,
    部分成功: 11
};
TK.LotteryType = {
    ssq双色球: 101,
    qlc七乐彩: 102,
    dlt大乐透: 103,
    jczq竞彩足球: 201,
    jclq竞彩篮球: 202,
    bjdc北京单场: 203,
    fc3d福彩3d: 151,
    qxc七星彩: 152,
    pl3排列三: 153,
    pl5排列五: 154,
    cqssc重庆时时彩: 4,
    jxssc江西时时彩: 5,
    xjssc新疆时时彩: 17,
    hljssc黑龙江时时彩: 21,
    tjssc天津时时彩: 27,
    hnssc湖南时时彩: 28,
    fjssc福建时时彩: 30,
    sd11x5山东11选5 : 16,
    jx11x5江西11选5 : 23,
    gd11x5广东11选5 : 24,
    cq11x5重庆11选5 : 36,
    tjkl10天津快乐十分: 15,
    gdkl10广东快乐十分: 22,
    xync重庆幸运农场: 33,
    gxkl10广西快乐十分: 34,
    shssl上海时时乐: 6,
    sdqyh山东群英会: 25,
    hnjlc湖南即乐彩: 26,
    bjkl8北京快乐8 : 35,
    jsk3江苏快3 : 3,
    ahk3安徽快3 : 32,
    weizhi未知: 255
};
TK.CurrentLotteryType = TK.LotteryType.weizhi未知;
TK.BetStatus = {};
TK.BetStatus.Enum = {销售: 1,
    停售: 2
};
TK.BetStatus.parse = function(a) {
    a = a || {};
    return {
        LotteryType: a.l || TK.LotteryType.weizhi未知,
        Status: a.s || TK.BetStatus.Enum.销售,
        Remark: a.r || "暂停销售"
    }
};
if ($S.Debug.IntelliSense) {
    for (var lt in TK.LotteryType) {
        TK.BetStatus[TK.LotteryType[lt]] = TK.BetStatus.parse({
            l: TK.LotteryType[lt]
        })
    }
}
Number.prototype.toLotteryName = function() {
    var b = this;
    var d = b.toString();
    switch (b) {
    default:
        for (var a in TK.LotteryType) {
            if (TK.LotteryType[a] == b) {
                switch (parseInt(b, 10)) {
                case 16:
                    d = "十一运夺金";
                    break;
                case 4:
                    d = "时时彩";
                    break;
                case 3:
                    d = "快3";
                    break;
                case 33:
                    d = "快乐十分";
                    break;
                default:
                    var c = new RegExp("([^\u4E00-\u9FA5]*)(.*)");
                    d = a.replace(c, "$2");
                    break
                }
                break
            }
        }
        break
    }
    return d
};
Number.prototype.toLotteryString = function() {
    var b = this;
    var d = b.toString();
    switch (b) {
    default:
        for (var a in TK.LotteryType) {
            if (TK.LotteryType[a] == b) {
                switch (parseInt(b, 10)) {
                default:
                    var c = new RegExp("([^\u4E00-\u9FA5]*)(.*)");
                    d = a.replace(c, "$1");
                    break
                }
                break
            }
        }
        break
    }
    return d
};
TK.Bet.PlayType = {};
TK.Bet.PlayType.parse = function(a) {};
TK.Bet.createBetMethod = function(a, c, b) {
    var d = "";
    for (var e in TK.Bet.BetMethod) {
        if (TK.Bet.BetMethod[e] != TK.Bet.BetMethod.常规选号 && TK.Bet.BetMethod[e] == a) {
            d = e;
            break
        }
    }
    if (d == "") {
        for (var e in TK.Bet.PlayType) {
            if (TK.Bet.PlayType[e] == c) {
                d = e + (typeof b != "undefined" ? b: "选号");
                break
            }
        }
    }
    return d
};
TK.Bet.ConvertResultStatus = function(d) {
    var e = $.extend({
        ObjStatus: {},
        DefaultText: null,
        OutputButton: []
    },
    d);
    var b = e.ObjStatus,
    a = e.DefaultText,
    c = e.OutputButton;
    var f = a || "投注失败";
    if ($S.Debug.IntelliSense) {
        b = new $S.JsonCommand()
    }
    switch (b.Command) {
    case 102:
        f = "密码错误";
        break;
    case 200:
        f = "账户余额不足";
        c.push('<a class="Dbutt" href="' + TK.Url.pay + '" target="_blank"><span>充 值</span></a>');
        c.push('<a class="cancel"><span>关 闭</span></a>');
        break;
    case 300:
        f = "未登录或登录超时";
        break;
    case 301:
    case 302:
        f = "投注金额有误";
        break;
    case 303:
        f = "投注号码有误";
        break;
    case 304:
        f = "投注奖期已截止";
        break;
    case 305:
        f = "投注奖期重复";
        break;
    case 307:
        f = "暂停销售";
        break;
    default:
        f = f + "(" + b.Command + ")";
        break
    }
    if (typeof(d) == "object" && typeof(d.OutputButton) == "object") {
        d.OutputButton = c
    }
    return f
}; (function(c) {
    function a() {
        function d() {
            this.defaultDateType = {当前日期: 1,
                触发对象的选定日期: 2
            }
        }
        this.enums = new d();
        function e() {
            this.Skin = "default";
            this.DateTime = new Date();
            this.MinYear = 1930;
            this.MaxYear = 2056;
            this.ClassName = {
                Wrap: "jquery_calendar"
            };
            this.AutoClosed = true;
            this.FadeTime = 300;
            this.DefaultDateType = new d().defaultDateType.当前日期;
            this.Format = "yyyy-MM-dd";
            this.TargetObject = null;
            this.Event_SelectDate = null
        }
        this.options = new e()
    }
    c.calendar = new a();
    function b(d) {
        var f = $("#jQuery_Calendar_Dacia");
        var h = "/js/plugin/calendar/skins/" + d + ".css";
        try {
            var i = $("script[src*=jquery-calendar]").attr("src").toUrl().segments;
            i.pop();
            h = "/" + i.join("/") + "/skins/" + d + ".css"
        } catch(g) {}
        if (f.length == 0) {
            $("head").append(String.format('<link href="{0}" rel="stylesheet" type="text/css" />', h))
        } else {
            $(f).attr("href", h)
        }
    }
    $.fn.calendar = function(E) {
        var d = this;
        function w(J, I) {
            this.FirstDate = new Date(J, I, 1);
            var i = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            this.FirstDay = this.FirstDate.getDay();
            this.DaysInMonth = I == 1 ? (((J % 4 == 0) && (J % 100 != 0) || (J % 400 == 0)) ? 29 : 28) : (i[I]);
            this.WeeksInMonth = (this.DaysInMonth + this.FirstDay) % 7 == 0 ? (this.DaysInMonth + this.FirstDay) / 7 : parseInt((this.DaysInMonth + this.FirstDay) / 7) + 1;
            this.LastDate = new Date(J, I, this.DaysInMonth);
            this.LastDay = this.LastDate.getDay()
        }
        function D(R) {
            function L(i) {
                if (M(i)) {
                    return ((N[i - 1900] & 65536) ? 30 : 29)
                } else {
                    return (0)
                }
            }
            function P(W) {
                var U, V = 348;
                for (U = 32768; U > 8; U >>= 1) {
                    V += (N[W - 1900] & U) ? 1 : 0
                }
                return (V + L(W))
            }
            function M(i) {
                return (N[i - 1900] & 15)
            }
            function Q(U, i) {
                return ((N[U - 1900] & (65536 >> i)) ? 30 : 29)
            }
            var N = new Array(19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43...te.getServerTime().toFormatString("{#yyyy#}-{#MM#}-{#dd#}")) {
                setTimeout(this.findCurrentIssueDom.bind(this), 100);
                return
            }
        } else {
            $(this.TempIssueDom).siblings("td").remove().end().parent().append(String.format('<td colspan="{0}"><a class="btn_sel" target="_blank" href="http://{1}/{2}/touzhu/">投注</a><span class="bettime">--:--</span></td>', this.Config.TypeColumnCount, TK.Url.pay.toUrl().host, this.LotteryType.toLotteryString()));
            $("td:eq(1)", $(kkListNumber.TempIssueDom).parent().parent().parent().prevAll("table").find("tr")).filter(function() {
                return $(this).html() == ""
            }).each(function() {
                $(this).html(g)
            });
            $("td:eq(1)", $(kkListNumber.TempIssueDom).parent().prevAll("tr")).filter(function() {
                return $(this).html() == ""
            }).each(function() {
                $(this).html(g)
            })
        }
    },
    getBottomTodayData: function() {
        if (this.ViewDate != $S.Date.getServerTime().toFormatString("{#yyyy#}-{#MM#}-{#dd#}")) {
            return
        }
        var a = this;
        $.ajax({
            url: "/handler/kuaikai/data.ashx",
            type: "post",
            data: {
                lottery: this.LotteryType,
                cmd: 1
            },
            success: function(b) {
                a.BottomTodayData = JSON.parse(b);
                $(a.DomBottomDom).remove();
                a.DomBottomDom = null;
                a.createBottomDom()
            }
        })
    },
    getBonusNumber: function(c, b, a) {
        var f = [],
        d = [],
        k = $S.Date.getServerTime().toFormatString("{#yyyy#}-{#MM#}-{#dd#} {#HH#}:{#mm#}");
        for (var g = 0; g < this.ListBonusNumber.length; g++) {
            if (this.ListBonusNumber[g].IssueNumber == b) {
                this.ListBonusNumber[g].BonusNumber = a;
                this.ListBonusNumber[g].EndTime = k;
                f = this.createItemAry(this.Config.ConvertType, this.ListBonusNumber[g]);
                break
            }
        }
        if (f.length == 0) {
            this.ListBonusNumber.push(new _IssueData({
                IssueNumber: b,
                BonusNumber: a,
                EndTime: k
            }));
            f = this.createItemAry(this.Config.ConvertType, this.ListBonusNumber[this.ListBonusNumber.length - 1])
        }
        if (f.length > 0) {
            for (var h = 0; h < f.length; h++) {
                d.push(String.format("<td{0}{2}>{1}</td>", f[h].Css == "" ? "": ' class="' + f[h].Css + '"', f[h].Content, f[h].Key != null ? ' key="' + f[h].Key + '"': ""))
            }
            $(this.DomNewWrap).find(String.format("td[key={0}]", b)).parent().html(d.join(""));
            this.getBottomTodayData()
        }
    },
    createBottomDom: function() {
        if (this.DomBottomDom == null && (this.BottomTodayData.length > 0 || this.BottomRecentData.length > 0)) {
            var f = [],
            g = [],
            a = [],
            b = [];
            switch (this.LotteryType) {
            case TK.LotteryType.cqssc重庆时时彩:
            case TK.LotteryType.jxssc江西时时彩:
                f = ["组三形态", "组六形态", "豹子形态", "组六最大连出"];
                a = [1, 0, 2, 3];
                g = ["组三出现次数", "组六出现次数", "组六最大连出", "豹子"];
                b = [1, 0, 3, 2];
                break;
            case TK.LotteryType.jsk3江苏快3:
            case TK.LotteryType.ahk3安徽快3:
                f = ["三同号", "三不同", "二同号", "三连号", "二同号最大连出值"];
                a = [0, 1, 3, 2, 5];
                g = ["三同号", "二同号", "二同号最大连出值", "二不同号", "三不同号", "三连号"];
                b = [0, 3, 5, 4, 1, 2];
                break
            }
            var h = [];
            h.push('<div class="bonusNum_foot">');
            h.push('<div class="fl">');
            h.push(String.format('<div class="tit"><strong>{0}今日({1})开奖号码形态统计</strong></div>', this.LotteryType.toLotteryName(), $S.Date.getServerTime().toFormatString("{#yyyy#}-{#MM#}-{#dd#}")));
            h.push('<div class="box">');
            for (var j = 0; j < f.length; j++) {
                h.push(String.format("<p>{1}：{0}次</p>", this.BottomTodayData[a[j]], f[j]))
            }
            h.push("</div>");
            h.push("</div>");
            h.push('<div class="fr">');
            h.push('<div class="tit"><strong>近7天开奖号码形态统计</strong></div>');
            h.push('<div class="box">');
            h.push("<table>");
            h.push("<tr><th>日期</th>");
            var d = 0,
            e = [];
            for (var j = 0; j < g.length; j++) {
                h.push(String.format("<th>{0}</th>", g[j]));
                e.push(0)
            }
            h.push("</tr>");
            for (var c in this.BottomRecentData) {
                h.push(String.format("<tr><td>{0}</td>", (c.substr(0, 4) + "-" + c.substr(4, 2) + "-" + c.substr(6, 2)).toDate().toFormatString("{#yyyy#}-{#MM#}-{#dd#}")));
                for (var j = 0; j < b.length; j++) {
                    h.push(String.format("<td>{0}</td>", this.BottomRecentData[c][b[j]]));
                    e[j] += this.BottomRecentData[c][b[j]]
                }
                h.push("</tr>");
                d++
            }
            h.push(String.format("<tr><td>{0}日平均</td>", d));
            for (var j = 0; j < e.length; j++) {
                h.push(String.format("<td>{0}</td>", parseInt(Math.round(e[j] / d), 10)))
            }
            h.push("</tr>");
            h.push("</table>");
            h.push("</div>");
            h.push("</div>");
            h.push("</div>");
            this.DomBottomDom = $(h.join("")).insertAfter(this.DomNewWrap)
        }
    },
    initialize: function(e, d, b, c) {
        if (window != top) {
            return
        }
        TK.CurrentLotteryType = e;
        this.LotteryType = e;
        this.initConfig();
        this.ListBonusNumber = d || [];
        this.IssueCountEveryDay = c;
        this.IsDefaultRencent = b == "0001-01-01";
        this.ViewDate = this.IsDefaultRencent ? $S.Date.getServerTime().toFormatString("{#yyyy#}-{#MM#}-{#dd#}") : b;
        var a = this;
        this.DomOldWrap = $("div.bonusNum_box");
        this.DomNewWrap = $('<div class="bonusNum_box newNum"></div>').insertAfter(this.DomOldWrap);
        this.DomHeadTip = $("div.bonusNum_tit strong");
        this.DomHeadLinkChange = $('<a class="fl"></a>').insertAfter(this.DomHeadTip);
        this.DomHeadDateRecent = $("div.bonusNum_tit div.fr a:eq(1)");
        var f = $(this.DomOldWrap).find("thead th.sort b").each(function(g) {
            $(this).click(a.out.bind(a, g > 0, linkHtml))
        });
        this.DomOldBody = $(this.DomOldWrap).find("table.data_tab").children("tbody");
        TK.Video.HasBetDataInfo = false;
        $(TK.Video).bind(TK.Video.Handler_Clock, null, this.clock.bind(this));
        $(TK.Video).bind(TK.Video.Handler_BetIssueEnd, null, this.findCurrentIssueDom.bind(this));
        $(TK.Video).bind(TK.Video.Handler_GetBonus, null, this.getBonusNumber.bind(this));
        TK.Video.initialize(null, (this.LotteryType == 4 ? "221.238.20.134": "221.238.20.131"), null);
        this.exchange()
    }
};
var kkListNumber = new ListNumber();
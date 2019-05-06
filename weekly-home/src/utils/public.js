const categoryItems = {
  10000: 1,
  10001: 3,
  10002: 2,
  1: 10000,
  3: 10001,
  2: 10002
}
const isRoundImg = function() {
  return './static/anonym/' + Math.floor(Math.random() * 10) + '.jpg'
}
const emotions = [
  {
    title: '默认',
    type: 'image',
    content: [
      { alt: '[微笑]', src: './static/face/huanglianwx_thumb.gif' },
      { alt: '[嘻嘻]', src: './static/face/tootha_thumb.gif' },
      { alt: '[哈哈]', src: './static/face/laugh.gif' },
      { alt: '[可爱]', src: './static/face/tza_thumb.gif' },
      { alt: '[可怜]', src: './static/face/kl_thumb.gif' },
      { alt: '[挖鼻]', src: './static/face/wabi_thumb.gif' },
      { alt: '[吃惊]', src: './static/face/cj_thumb.gif' },
      { alt: '[害羞]', src: './static/face/shamea_thumb.gif' },
      { alt: '[挤眼]', src: './static/face/zy_thumb.gif' },
      { alt: '[闭嘴]', src: './static/face/bz_thumb.gif' },
      { alt: '[鄙视]', src: './static/face/bs2_thumb.gif' },
      { alt: '[爱你]', src: './static/face/lovea_thumb.gif' },
      { alt: '[泪]', src: './static/face/sada_thumb.gif' },
      { alt: '[偷笑]', src: './static/face/heia_thumb.gif' },
      { alt: '[亲亲]', src: './static/face/qq_thumb.gif' },
      { alt: '[生病]', src: './static/face/sb_thumb.gif' },
      { alt: '[太开心]', src: './static/face/mb_thumb.gif' },
      { alt: '[白眼]', src: './static/face/landeln_thumb.gif' },
      { alt: '[右哼哼]', src: './static/face/yhh_thumb.gif' },
      { alt: '[左哼哼]', src: './static/face/zhh_thumb.gif' },
      { alt: '[嘘]', src: './static/face/x_thumb.gif' },
      { alt: '[衰]', src: './static/face/cry.gif' },
      { alt: '[委屈]', src: './static/face/wq_thumb.gif' },
      { alt: '[吐]', src: './static/face/t_thumb.gif' },
      { alt: '[哈欠]', src: './static/face/haqianv2_thumb.gif' },
      { alt: '[抱抱]', src: './static/face/bba_thumb.gif' },
      { alt: '[怒]', src: './static/face/angrya_thumb.gif' },
      { alt: '[疑问]', src: './static/face/yw_thumb.gif' },
      { alt: '[馋嘴]', src: './static/face/cza_thumb.gif' },
      { alt: '[拜拜]', src: './static/face/88_thumb.gif' },
      { alt: '[思考]', src: './static/face/sk_thumb.gif' },
      { alt: '[汗]', src: './static/face/sweata_thumb.gif' },
      { alt: '[困]', src: './static/face/kunv2_thumb.gif' },
      { alt: '[睡]', src: './static/face/huangliansj_thumb.gif' },
      { alt: '[钱]', src: './static/face/money_thumb.gif' },
      { alt: '[失望]', src: './static/face/sw_thumb.gif' },
      { alt: '[酷]', src: './static/face/cool_thumb.gif' },
      { alt: '[色]', src: './static/face/huanglianse_thumb.gif' },
      { alt: '[哼]', src: './static/face/hatea_thumb.gif' },
      { alt: '[鼓掌]', src: './static/face/gza_thumb.gif' },
      { alt: '[晕]', src: './static/face/dizzya_thumb.gif' },
      { alt: '[悲伤]', src: './static/face/bs_thumb.gif' },
      { alt: '[抓狂]', src: './static/face/crazya_thumb.gif' },
      { alt: '[黑线]', src: './static/face/h_thumb.gif' },
      { alt: '[阴险]', src: './static/face/yx_thumb.gif' },
      { alt: '[怒骂]', src: './static/face/numav2_thumb.gif' },
      { alt: '[互粉]', src: './static/face/hufen_thumb.gif' },
      { alt: '[心]', src: './static/face/hearta_thumb.gif' },
      { alt: '[伤心]', src: './static/face/unheart.gif' },
      { alt: '[猪头]', src: './static/face/pig.gif' },
      { alt: '[熊猫]', src: './static/face/panda_thumb.gif' },
      { alt: '[兔子]', src: './static/face/rabbit_thumb.gif' },
      { alt: '[ok]', src: './static/face/ok_thumb.gif' },
      { alt: '[耶]', src: './static/face/ye_thumb.gif' },
      { alt: '[good]', src: './static/face/good_thumb.gif' },
      { alt: '[NO]', src: './static/face/buyao_org.gif' },
      { alt: '[赞]', src: './static/face/z2_thumb.gif' },
      { alt: '[来]', src: './static/face/come_thumb.gif' },
      { alt: '[弱]', src: './static/face/sad_thumb.gif' },
      { alt: '[草泥马]', src: './static/face/shenshou_thumb.gif' },
      { alt: '[神马]', src: './static/face/horse2_thumb.gif' },
      { alt: '[囧]', src: './static/face/j_thumb.gif' },
      { alt: '[浮云]', src: './static/face/fuyun_thumb.gif' },
      { alt: '[给力]', src: './static/face/geiliv2_thumb.gif' },
      { alt: '[围观]', src: './static/face/wg_thumb.gif' },
      { alt: '[威武]', src: './static/face/vw_thumb.gif' },
      { alt: '[奥特曼]', src: './static/face/otm_thumb.gif' },
      { alt: '[礼物]', src: './static/face/liwu_thumb.gif' },
      { alt: '[钟]', src: './static/face/clock_thumb.gif' },
      { alt: '[话筒]', src: './static/face/huatongv2_thumb.gif' },
      { alt: '[蜡烛]', src: './static/face/lazhuv2_thumb.gif' }
    ]
  },
  {
    title: 'emoji',
    type: 'emoji',
    content: ['😀', '😁', '😂', '😃', '😄', '😅', '😆', '😉', '😊', '😋', '😎', '😍', '😘', '😗', '😙', '😚', '😇', '😐', '😑', '😶', '😏', '😣', '😥', '😮', '😯', '😪', '😫', '😴', '😌', '😛', '😜', '😝', '😒', '😓', '😔', '😕', '😲', '😷', '😖', '😞', '😟', '😤', '😢', '😭', '😦', '😧', '😨', '😬', '😰', '😱', '😳', '😵', '😡', '😠']
  },
  {
    title: '动物园',
    type: 'emoji',
    content: ['🙈', '🙉', '🙊', '🐵', '🐒', '🐶', '🐕', '🐩', '🐺', '🐱', '😺', '😸', '😹', '😻', '😼', '😽', '🙀', '😿', '😾', '🐈', '🐯', '🐅', '🐆', '🐴', '🐎', '🐮', '🐂', '🐃', '🐄', '🐷', '🐖', '🐗', '🐽', '🐏', '🐑', '🐐', '🐪', '🐫', '🐘', '🐭', '🐁', '🐀', '🐹', '🐰', '🐇', '🐻', '🐨', '🐼', '🐾', '🐔', '🐓', '🐣', '🐤', '🐥', '🐦', '🐧', '🐸', '🐊', '🐢', '🐍', '🐲', '🐉', '🐳', '🐋', '🐬', '🐟', '🐠', '🐡', '🐙', '🐚', '🐌', '🐛', '🐜', '🐝', '🐞', '🦋']
  }
]

export default {
  categoryItems,
  emotions,
  isRoundImg
}

(function($){
  $.fn.badges = function(options) {
    $.ajaxSetup({ cache: false });
    
    var settings = $.extend({
        url: "badges.json?employeeId=&challengeYear=",
        goalsMet: 0,
        badgeInfoUrl: 'badges.html',
        badgeDirectory: "images/badges/"
      }, options );
      
    $.getJSON(settings.url, renderBadges);  
    var $el = this;
    
    function renderBadges(badges) {
      $.each(badges, function(i, badge) {
        addBadge(badge.image, badge.label);
      });
      showBadgeInfoLinkIfNoBadgesEarned();
    }  
    
    function showBadgeInfoLinkIfNoBadgesEarned() {
      if ($el.html() === '') {
        $el.html("<a href='"+ settings.badgeInfoUrl +"' class='small'>earn a badge</a>");
      }
    }
    
    function addBadge(img, title) {
      $el.append("<a href='"+ settings.badgeInfoUrl +"'><img class='' src='" + settings.badgeDirectory + img + "' width='60' title='" + escapeHtml(title) + "'/></a>");
    }
    
    function escapeHtml(str) {
      return str.replace(/&/g, '&amp;')
                .replace(/>/g, '&gt;')
                .replace(/</g, '&lt;')
                .replace(/"/g, '&quot;')
                .replace(/'/g, '&apos;');
    }
  };
})(jQuery);
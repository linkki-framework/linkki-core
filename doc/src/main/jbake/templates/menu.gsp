<!-- Fixed navbar -->
<div class="book font-size-2 font-family-1 with-summary">
  <div class="book-summary">
    <% if(config.logo != "false"){%>
<a class="navbar-brand" href="${config.homelink}"><img src="<%if (content.rootpath.length() > 0) {%>${content.rootpath}<% } else { %><% }%>images/${config.logo}" style="padding-top:8px;padding-right:20px;padding-left:20px"></a>
    <%}%>
    <form  id="book-search-input" role="search" action="<%if (content.rootpath) {%>${content.rootpath}<% } else { %><% }%>search/search.html">
      <input type="text" name="q" id="tipue_search_input" placeholder="Type to search" pattern=".{3,}" title="At least 3 characters" required>
    </form >
    <nav role="navigation">
      <ul class="summary">

      <!--  Home -->
      <%if(content.uri == null || content.uri.indexOf('/') < 0 ) {%>
        <li class="chapter active">
      <%} else {%>
        <li class="chapter ">
      <%}%>
          <a href="<%if (content.rootpath) {%>${content.rootpath}<% } else { %><% }%>index.html">Home</a>
      </li><!-- end home -->

      <!--  Chapters -->
      <%
      orderedChapters = published_chapters.sort{ it.order }
      orderedChapters.each { chapter ->
        if(chapter.uri == content.uri){%>
        <li class="chapter active">
        <%} else {%>
        <li class="chapter ">
        <%}%>
          <a href="<%if (content.rootpath) {%>${content.rootpath}<% } else { %><% }%>${chapter.uri}">${chapter.title}</a>

          <%
            chapterName = chapter.uri.substring(0, chapter.uri.indexOf('/'))
            currentChapterName = content.uri.substring(0, Math.max(content.uri.indexOf('/'), 0))
            if(chapterName == currentChapterName){
          %>
              <ul class="articles">
              <%
                sectionsByFileName = published_sections.sort{ it.uri }
                sectionsByOrder = sectionsByFileName.sort{ it.order }
                sectionsByOrder.each { section ->
                  sectionChapterName = section.uri.substring(0, section.uri.indexOf('/'))
                  if(sectionChapterName == chapterName && sectionChapterName) {
                    if(section.uri == content.uri) {%>
                      <li class="chapter active">
                    <%}else{%>
                      <li class="chapter ">
                    <%}%>
                      <a href="<%if (content.rootpath.length() > 0) {%>${content.rootpath}<% } else { %><% }%>${section.uri}">${section.title}</a>
                    </li><!--end section -->
                  <%}%>
              <%}%>
            </ul>
          <%}%>
	       </li>
      <%}%>

      </ul> <!-- summary -->
  </nav>
</div>
<!-- closing </div> in footer -->

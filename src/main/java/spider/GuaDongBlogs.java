package spider;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GuaDongBlogs implements PageProcessor {

    private Site site = Site.me().setDomain("www.guadong.net")
            .addStartUrl("http://www.guadong.net/");

    public static void main(String[] args) {
        Spider.create(new GuaDongBlogs())
                .pipeline(new ConsolePipeline()).run();
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links()
                .regex("http://www\\.guadong\\.net").all();
        page.addTargetRequests(links);
        page.putField(
                "title",
                page.getHtml()
                        .xpath("//div[@id='container']/div[@id='posts']/div[@class='post']/h2")
                        .toString());
     
        // page.putField("content", page.getHtml().$("div.content").toString());
        page.putField("tags",
                page.getHtml().
                xpath("//div[@id='container']/div[@id='posts']/div[@class='post']/div[@class='tags']/text()").all());
    }

}

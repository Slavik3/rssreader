package com.edvantis.rssreader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.edvantis.rssreader.controller.RssServiceTest;
import com.edvantis.rssreader.services.GetDomainNameTest;

@RunWith(Suite.class)
@SuiteClasses({RssServiceTest.class, GetDomainNameTest.class})
public class AllTests {

}

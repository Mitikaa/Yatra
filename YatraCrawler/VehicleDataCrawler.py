import urlparse
import urllib
from BeautifulSoup import BeautifulSoup
#!/usr/bin/python

import sys

def main():

    urls = []
    urls.insert(1, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Buick&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(2, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Acura&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(3, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chrysler&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(4, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chrysler&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(5, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Dodge&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(6, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Dodge&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(7, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Dodge&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(8, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=GMC&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(9, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=GMC&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(10, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=GMC&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(11, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Jeep&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(12, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Jeep&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(13, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Jaguar&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(14, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Jaguar&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(15, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Land+Rover&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(16, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Lexus&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(17, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Lincoln&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(18, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mazda&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(19, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mazda&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(20, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercury&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(21, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Saab&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(22, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Subaru&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(23, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Suzuki&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(24, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(25, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(26, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(27, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(28, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=5&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(29, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=BMW&srchtyp=newMake&pageno=6&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(30, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Cadillac&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(31, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Cadillac&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(32, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(33, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(34, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(35, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=5&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(36, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=6&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(37, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Chevrolet&srchtyp=newMake&pageno=7&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(38, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Ford&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(39, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Ford&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(40, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Ford&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(41, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Ford&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(42, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Ford&srchtyp=newMake&pageno=5&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(43, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Honda&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(44, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Honda&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(45, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Hyundai&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(46, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Hyundai&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(47, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Hyundai&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(48, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Infiniti&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(49, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Infiniti&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(50, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Kia&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(51, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Kia&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(52, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercedes-Benz&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(53, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercedes-Benz&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(54, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercedes-Benz&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(55, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercedes-Benz&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(56, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mercedes-Benz&srchtyp=newMake&pageno=5&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(57, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=MINI&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(58, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=MINI&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(59, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mitsubishi&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=100");
    urls.insert(60, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Mitsubishi&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=100");
    urls.insert(61, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Nissan&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(62, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Nissan&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(63, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Nissan&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(64, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Nissan&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(65, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Scion&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(66, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Smart&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&tabView=0&rowLimit=200");
    urls.insert(67, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Toyota&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(68, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Toyota&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(69, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Toyota&srchtyp=newMake&pageno=3&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(70, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Toyota&srchtyp=newMake&pageno=4&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(71, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Volvo&srchtyp=newMake&pageno=1&sortBy=Comb&tabView=0&rowLimit=200");
    urls.insert(72, "http://www.fueleconomy.gov/feg/PowerSearch.do?action=noform&path=4&year1=2005&year2=2017&make=Volvo&srchtyp=newMake&pageno=2&sortBy=Comb&tabView=0&rowLimit=200");



    tablehead = 'CarModel,ImgUrl,MPG,UserEco\n'
    # EngineDisplacement,Transmission,FuelType,
    while len(urls) > 0:
        try:
            htmltext = ''
            htmltext = urllib.urlopen(urls[0]).read()
        except:
            print urls[0]

        soup = BeautifulSoup(htmltext)
        urls.pop(0)

        table = soup.find('table', attrs={'class': 'cars display responsive stickyHeader'})

        for table_body in table.findAll('tbody'):
            rowNum = 0
            if table_body:
                rows = table_body.findAll('tr')
                for row in rows:
                    if rowNum == 0:
                        model = row.find('td').find('a', href=True)
                        #detail = row.find('span', attrs={'class': 'config'})
                        if model:
                            tablehead = tablehead + ' ' + model.text # + '  ' + detail.text

                    if row.find('td', attrs={'class': 'mpg-epa'}):
                        imgurl = row.find('td', attrs={'class': 'vphoto'}).find('img', attrs={'class': 'img-thumbnail img-responsive veh-photo'})
                        fuelecoTable = ''
                        try:
                            fuelecoTable = row.find('td', attrs={'class': 'mpg-epa'}).find('div',attrs={'class': 'panel panel-default mpg-border'}).find('div',attrs={'class':'panel-body'}).find('table',attrs={'class':'results'}).findAll('tbody')
                            fueleco = ''
                            for fuelecorow in fuelecoTable:
                                fueleco = fuelecorow.find('td', attrs={'class': 'mpg-comb'})
                                break
                        except:
                            pass
                        finally:
                            if fuelecoTable is not '':
                                usereco = row.find('td', attrs={'class': 'mpg-user'})
                                tablehead = tablehead + ',' + str(imgurl['src'])
                                tablehead = tablehead + ',' + str(fueleco.text)
                                tablehead = tablehead + ',' + str(usereco.text)
                                tablehead = tablehead + '\n'
                    rowNum += 1
        #tablehead = tablehead + '\n'
    tablehead = tablehead + '\n'
    f = open('Output.csv','w')
    f.write(str(tablehead))
    print tablehead
pass

if __name__ == '__main__':
    main()
# DRIP GEEK
![](pic/dripGeek.jpg)

_Inspired by [Drip](https://drip.community/) ([whitepaper](https://drip.community/docs/DRIP_LIGHTPAPER_v0.8_Lit_Version.pdf)), designed for Drip geeks with multi wallets_

## Overview

YO! You can hydrate all your wallets AT ONCE! BUT WAIT. THERE'S MORE. 

- This is a multi wallet first, command line tool for managing your drip investment
- Check the balances of all your wallets
- Manually send bnb from one wallet to any number of your wallets 
- Automatically send bnb to low balance wallets based on a configuration 
  - Use this on its own or have it run automatically right before you hydrate
- Control which wallets are included in or excluded from all actions
- Get a report on all of your wallets to know your BNB balance, Faucet balance and more

## Installation
- You need java installed on your machine
- Tested for Java 13, Macs and Windows 11 (You'll need to replace `/` with `\` for windows)
- Run ` ./gradlew build ` to build the project and generate `drip-geek.jar`

## Task Cheat Sheet
All task are run in the following form: `java -jar cli/build/libs/drip-geek.jar -flag` where `-flag` may be 1 of the following task names but not zero.
Only "Find, Fund and Hydrate" may have more than 1 flag


| Task Name                                          | Flag                                         | Description                                                                                                                                                        |
|----------------------------------------------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Hydrate                                            | -hydrate_wallets                             | Hydrate all included wallets based on settings in .env file                                                                                                         |
| Find and Fund                                      | -find_and_fund_wallets                        | Finds all fundable wallets with a balance at or below  $MIN_BNB_BALANCE and sends them the amount of BNB defined in $FUND_BNB_BALANCE                               |
| Find, Fund and Hydrate                             | -find_and_fund_wallets <br> -hydrate_wallets  | Runs both tasks at once                                                                                                                                            |
| Wallet BNB Balance Check                           | -bnb_balances_wallets                        | Print BNB balances for all included wallets                                                                                                                        |
| Send BNB to Wallets                                | -send_bnb_to_wallets <amount to send>        | Send BNB from a main wallet at $MAIN_BNB_WALLET to 1 or more other included wallets based on settings in .env file. Pass "amount to send" as a parameter to the flag |
| Wallet Report                                      | -summarize_wallets                           | Print wallet reports and wallets summary for all included wallets                                                                                                  |
| Generate Private Key                               | -generate_key                                | Generates a random secret key                                                                                                                                      |
| Generate Tokens                                    | -generate_tokens                             | Takes a comma separated list of strings at $CONVERT_TO_TOKENS and tokenizes them and then lists them in console                                                    |
| Claim to Drip **                                   | -claim_drip                                  | Claims $Drip in wallets available balances                                                                                                                          |
| Claim to Drip, swap to BNB **                      | -claim_drip_to_bnb                           | Claims $Drip in wallets available balances, swaps the claimed $Drip to BNB                                                                                          |
| Claim to Drip, swap to BNB, Send to Main Wallet ** | -claim_to_bnb_send_to_main_wallet            | Claims $Drip in wallets available balances, swaps the claimed $Drip to BNB then sends to main wallet                                                                |
| Claim to Drip, swap to USDC, Send to Main Wallet **| -claim_to_usdc_send_to_main_wallet           | Claims $Drip in wallets available balances, swaps the claimed $Drip to BNB then sends to main wallet                                                                |
** = coming soon!
Configuration for this project is done with a `.env` file. For more info check out the [The .env file](#the-env-file) section

## Disclaimer and Truth
My friends, I am not that guy. I don't want your money. Stealing other people's money kinda disrespects the whole point of the original spirit of crypto IMO... but I'm not even going to go there. I wrote this because I wanted to use it myself, but I'm open sourcing it because maybe others can use it. Ask one of your buddies who is an engineer to review this before you use it. Hell, pay someone to. You'll see this is all safu safe boring code that only does exactly what I say it does. I'm pretty obsessive so there shouldn't be any bugs but I can't say never so reach out if there is something weird and I'll fix it.

## Getting Started: How to configure everything
### Secrets!
- First off if you don't trust yourself to handle your keys maybe this isn't for you
- You have to get the private key of your wallets from your MetaMask or your Ledger
- You then add these private keys to a `.env` file (along with other things) that will **not** be committed to Github (more on that later) so no one can see it but you

Prerequisite:
[How to get your private keys from MetaMask](https://metamask.zendesk.com/hc/en-us/articles/360015289632-How-to-Export-an-Account-Private-Key)
or
[How to get your private keys from your ledger](https://www.youtube.com/watch?v=ZnSBer66W8s) (the only thing this guy missed is you might need to cycle through all the account numbers to get all your wallet addresses)

## The .env file
This is our main configuration file. We will define variables in this file that the program will use to carry out your wishes. This project is set up such that you can edit this file but it will never get checked into github. Keep it that way. Check out [.example.env](.example.env), make a copy, remove ".example" from the name of the file and fill out your details. The `.env` file has 6 types of environment variables that can be listed in no particular order (here and in the file):

|  Type                           | Name               |    
|---------------------------------|--------------------|   
|  Wallet Name                    | `__DRIP_whatever`  |   
|  Main Wallet                    | `MAIN_BNB_WALLET`  |   
|  Secret Key                     | `SECRET_KEY`       |   
|  Min BNB Balance                | `MIN_BNB_BALANCE`  |   
|  Fund BNB Balance               | `FUND_BNB_BALANCE` |   
|  Convert Private Keys to Tokens | `CONVERT_TO_TOKENS`|   

*note*: There is a `.env` file in the test resources folder. It is a copy of the contents of `.example.env`. You can ignore it. 

#### Wallet Name
The wallet name must start with `__DRIP_`. So you can make it `__DRIP_HOT_DOG_BANANANA_SAUCE_A` for all I care and it will work. There is no theoretical limit on the amount of wallets you can list (one per line) but I tested it on 25. When displayed via the console wallets are sorted alphabetically Set the wallet name to a string that consists of 5 comma separated **parts**(order matters here):

|  Part Name | Description |  Type | 
|---|---|---|
| Include In Hydrations and Reports | a boolean (true or false) for whether to include the wallet in Reports and hydrations regardless of deposit size (more on that later) |  Boolean | 
| Include in Money Sending |  a boolean (true or false) for whether to include the wallet when we are determining which wallet to send BNB to | Boolean  | 
| Wallet Name  | The wallet name used mostly in the console  | String  | 
| Wallet Address | The wallet address  | Wallet Address | 
| Tokenized Private Key  | The wallet private key (please be safe and don't share this with anyone, don't email it to your self or keep it in the notes in your phone or something like that.)  |  Tokenized String | 

So for example `__DRIP_HOT_DOG_BANANANA_SAUCE_A="true,true,DRIP_HOT_DOG_BANANANA_SAUCE_A,0xbe0eb53f46cd790cd13851d5eff43d12404d33e8,gAAAAABiQj5jonwif0vj2wf0joigj2wjr02fioj2goijiw4jg0jw0gjwigj0j0gw20jq20ij92h4g712g719h292hhfgufqwigviy1fbv1iebw-87wfbcviu2if2vhveoinwvoiwonbwefvqbvow8tghh3g0wvwj0jf20w0gjwerg0wjjiwfow="`

#### Main Wallet
This has nothng to do with the "Drip" version of a main wallet. This is used by the [Find and Fund Low Balances](#find-and-fund-low-balance-wallets) task and the [Send BNB](#send-bnb-to-1-or-more-wallet) task. This is the wallet you want to keep a good amount of BNB in. The environment variable that is the main wallet is `MAIN_BNB_WALLET`. This program will distribute that bnb to all your other wallets from this wallet. It should be a copy of 1 of your other wallets. So to continue our example if `sldkfjs` was also our main wallet there would be another entry in the .env file like this:
For example `MAIN_BNB_WALLET="true,true,DRIP_A_WALLET,0xbe0eb53f46cd790cd13851d5eff43d12404d33e8,<some-stupidly-long-string-that-is-your-private-key-tokenized>"`

It doesn't matter what you put for the second boolean the main wallet will never be fundable but I won't stop you from trying to fund it via a regular wallet environment variable if you are nutty like that

#### Secret Key
The expected name of the environment variable secret key of the program is `SECRET_KEY`. To generate the secret key run: 

`java -jar cli/build/libs/drip-geek.jar -generate_key`

Take the generated secret key and set it in the `.env` file like this `SECRET_KEY=<generated key>`. Also think about clearing your command line after. 

#### Min BNB Balance
The expected name of the environment variable that is the bnb balance that triggers a money reload to is `MIN_BNB_BALANCE`. This is used by the [Find and Fund Low Balances](#find-and-fund-low-balance-wallets) task. I recommend setting this to `0.0030` because after trial and error it seems like less than this amount will cause your transactions to fail due to insufficient funds. You can also just set this value to zero to cause all wallet to be funded by the Find and Fund Low Balances task.

#### Fund Balance
The expected name of the environment variable that is the amount of bnb to automatically send to wallets is `FUND_BNB_BALANCE`. This is used by the [Find and Fund Low Balances](#find-and-fund-low-balance-wallets) task. I recommend setting this to `0.008` which will get me about 4-6 days of compounding. Experiment with what makes sense for you. 

Here is an [example](.example.env) `.env` file. All the details were made up except the wallet addresses are the largest bnb wallet addresses at this time because I thought that was cool. 

#### Convert your private keys to tokens
After you have got a list of all the private keys from your MetaMask or Ledger wallets, comma separate them and assign them to the `CONVERT_TO_TOKENS` environment variable.

`CONVERT_TO_TOKENS=private_key,private_key,private_key,private_key` 

After that you can run the Generate Tokens task to get a list of tokenized private keys

`java -jar cli/build/libs/drip-geek.jar -generate_tokens`

Then take each and add them to your wallet name environment variable. So if your private key is `foo` and your token is `bar` then from our example earlier here is how you would set your tokenized private key: 

`__DRIP_HOT_DOG_BANANANA_SAUCE_A="true,true,DRIP_HOT_DOG_BANANANA_SAUCE_A,0xbe0eb53f46cd790cd13851d5eff43d12404d33e8,bar"`

But in reality this will be a much longer string. 

When you are done I suggest you delete `CONVERT_TO_TOKENS` and your private keys from your `.env` file

## Known Issues
- `502 Bad Gateway` when trying to run the tool. Happens while the program is trying to connect to `https://api.drip.community/prices`. Its transient, just run the program again.

## TODO
- I forgot to add claiming lol :facepalm
- Right now it will hydrate all wallets specified however in the future you will be able to set minimum available balance that triggers a hydrate at several deposit sizes

## Presented to you by... 
![](pic/BoredDripApeClub.png) <br><br>
If you are thinking of joining drip, consider using our team wallet address as your buddy address: `0xb8251ae17B38A79B27e80eb68974Fdb766c89236`

## Consulting
If you'd like something special I'm happy to whip it up for a reasonable fee. Open an issue. 

Also check out some projects that inspired me:
- [Logicfool's](https://github.com/logicfool) [AutoDripBot](https://github.com/logicfool/AutoDripBot)
- [Ymagoon's](https://github.com/ymagoon) [Drip-Autohydrator](https://github.com/ymagoon/web3/tree/main/drip-autohydrater)

## Bonus: Road to DRIP 2500

- Stolen from some Telegram message
- The power of daily compounding in DRIP Faucet💦
- 1% Daily ROI (0.95% less 5% Tax Fee)

|  Drip       | Days               |    
|-------------|--------------------|   
|10 to 20     | 74 days💧          |
|10 to 30     | 117 days💧         |
|10 to 40     | 147 days💧         |
|10 to 50     | 171 days💧         |
|10 to 60     | 190 days💧         |
|10 to 70     | 206 days💧         |
|10 to 80     | 220 days💧         |
|10 to 90     | 233 days💧         |
|10 to 100    | 244 days💧         |
|100 to 200   | 73 days (Day 317)💧|
|200 to 300   | 43 days (Day 360)💧|
|300 to 400   | 31 Days (Day 391)💧|
|400 to 500   | 23 Days (Day 414)💧|
|500 to 600   | 20 Days (Day 434)💧|
|600 to 700   | 16 Days (Day 450)💧|
|700 to 800   | 14 days (Day 464)💧|
|800 to 900   | 12 Days (Day 476)💧|
|900 to 1000  | 12 days (Day 488)💧|
|1000 to 1100 | 10 days (Day 498)💧|
|1100 to 1200 | 09 days (Day 507)💧|
|1200 to 1300 | 08 days (Day 515)💧|
|1300 to 1400 | 08 days (Day 523)💧|
|1400 to 1500 | 07 days (Day 530)💧|
|1500 to 1600 | 07 days (Day 537)💧|
|1600 to 1700 | 07 days (Day 544)💧|
|1700 to 1800 | 06 days (Day 550)💧|
|1800 to 1900 | 05 days (Day 555)💧|
|1900 to 2000 | 06 days (Day 561)💧|
|2000 to 2500 | 23 days (Day 584)💧|

Focus, Determination and Discipline🔥
💧💧💧💧💰💰💰💰🤑
DRIP = Time + Financial Freedom
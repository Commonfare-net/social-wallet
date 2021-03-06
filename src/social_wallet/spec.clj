;; social-wallet - A generic social wallet UI which uses the social-wallet-api for a beckend

;; Copyright (C) 2019- Dyne.org foundation

;; Sourcecode designed, written and maintained by
;; Aspasia Beneti <aspra@dyne.org>

;; This program is free software; you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

;; This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

;; You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.

;; Additional permission under GNU AGPL version 3 section 7.

;; If you modify this Program, or any covered work, by linking or combining it with any library (or a modified version of that library), containing parts covered by the terms of EPL v 1.0, the licensors of this Program grant you additional permission to convey the resulting work. Your modified version must prominently offer all users interacting with it remotely through a computer network (if your version supports such interaction) an opportunity to receive the Corresponding Source of your version by providing access to the Corresponding Source from a network server at no charge, through some standard or customary means of facilitating copying of software. Corresponding Source for a non-source form of such a combination shall include the source code for the parts of the libraries (dependencies) covered by the terms of EPL v 1.0 used as well as that of the covered work.

(ns social-wallet.spec
  (:require [taoensso.timbre :as log]
            [clojure.spec.alpha :as spec]))

(spec/def ::mongo-config (spec/keys :req-un [::host ::port ::db]))
(spec/def ::throttling (spec/keys :req-un [::criteria ::type ::time-window-secs ::threshold]))
(spec/def ::just-auth (spec/keys :req-un [::email-config ::mongo-config ::throttling]))
(spec/def ::security (spec/keys :req-un [::anti-forgery ::ssl-redirect]))
(spec/def ::webserver (spec/keys :req-un [::security]))
(spec/def ::swapi (spec/keys :req-un [::base-url ::apikey-file ::apikey-name]))
(spec/def ::noapikey-swapi (spec/keys :req-un [::base-url]))
(spec/def :social-wallet.config/noapikey-config (spec/keys :req-un [::webserver ::just-auth ::noapikey-swapi]))
(spec/def :social-wallet.config/config (spec/keys :req-un [::webserver ::just-auth ::swapi]))

(spec/def :social-wallet.authenticator/email-conf (spec/keys :req-un [::email-server ::email-user ::email-pass ::email-address]))
(spec/def :social-wallet.core/email-conf-admin  (spec/keys :req-un [:social-wallet.core/email-conf ::email-admin]))

;; Sendto form
(spec/def :social-wallet.handler/tags vector?)
(spec/def :social-wallet.handler/to string?)
(spec/def :social-wallet.handler/amount (and decimal? #(> % 0.0)))
(spec/def :social-wallet.webpage/sendto (spec/keys :req-un [:social-wallet.hanler/amount :social-wallet.hanler/to :social-wallet.hanler/tags]))
